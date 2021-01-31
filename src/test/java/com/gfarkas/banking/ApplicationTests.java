package com.gfarkas.banking;

import com.gfarkas.banking.dto.AccountDTO;
import com.gfarkas.banking.exception.AccountAlreadyExistsException;
import com.gfarkas.banking.exception.AccountNotFoundException;
import com.gfarkas.banking.exception.InSufficientFundException;
import com.gfarkas.banking.model.Currency;
import com.gfarkas.banking.model.Money;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = Application.class)
class ApplicationSpringTests {

	final static String JOHN_DOE = "John Doe";
	final static String JANE_DOE = "Jane Doe";
	final static String USD = "USD";
	final static String EUR = "EUR";
	final static String HUNDRED = "100";

	@Test
	void contextLoads() {
		System.out.println("This is a SpringBoot context test");
	}

	@Test
	void createAccount() throws AccountAlreadyExistsException {
		AccountDTO savedAccount = newAccount(JOHN_DOE, "USD", HUNDRED, new BigDecimal("1.00"), false);

		Assertions.assertNotNull(savedAccount);
	}

	@Test
	void readAccount() throws AccountAlreadyExistsException {
		newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		AccountDTO savedAccount = service.findByName(JOHN_DOE);

		Assertions.assertNotNull(savedAccount);
	}

	@Test
	void readAccountWithWrongName() throws AccountNotFoundException, AccountAlreadyExistsException {
		newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);

		Assertions.assertThrows(AccountNotFoundException.class,
				() -> service.findByName("Wrong Name"));
	}

	@Test
	void updateAccount() throws AccountNotFoundException, AccountAlreadyExistsException {
		AccountDTO accountToModify = newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		accountToModify.setName(JANE_DOE);
		AccountDTO modifiedAccount = service.update(accountToModify, JOHN_DOE);

		Assertions.assertEquals(JANE_DOE, modifiedAccount.getName());
	}

	@Test
	void updateAccountDoesNotChangeTreasury() throws AccountNotFoundException, AccountAlreadyExistsException {
		newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		AccountDTO account = new AccountDTO();
		account.setTreasury(true);
		account.setName(JOHN_DOE);
		account.setCurrency(new Currency("USA Dolar",USD, new BigDecimal("1.00")));
		account.setMoney(new Money(new BigDecimal(HUNDRED)));
		AccountDTO modifiedAccount = service.update(account, account.getName());

		Assertions.assertEquals(false, modifiedAccount.getTreasury());
	}

	@Test
	void transferBetweenAccounts() throws AccountNotFoundException, AccountAlreadyExistsException {
		newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		newAccount(JANE_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		service.transfer(JOHN_DOE, JANE_DOE, new BigDecimal("100.00"));

		AccountDTO accountOne = service.findByName(JOHN_DOE);
		AccountDTO accountTwo = service.findByName(JANE_DOE);

		Assertions.assertEquals(new BigDecimal("0.00"), accountOne.getMoney().getAmount());
		Assertions.assertEquals(new BigDecimal("200.00"), accountTwo.getMoney().getAmount());
	}

	@Test
	void cannotTransferMoreThanYouHaveOnYourNonTreasuryAccount() throws AccountAlreadyExistsException {
		AccountDTO accountOne = newAccount(JOHN_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);
		AccountDTO accountTwo = newAccount(JANE_DOE, USD, HUNDRED, new BigDecimal("1.00"), false);

		Assertions.assertThrows(InSufficientFundException.class,
				() -> service.transfer(JOHN_DOE, JANE_DOE, new BigDecimal("101.00")));
	}

	@Test
	void cannotTransferBetweenDifferentCurrencies() throws AccountAlreadyExistsException {
		newAccount(JOHN_DOE, EUR, HUNDRED, new BigDecimal("0.82"),true);
		newAccount(JANE_DOE, USD, HUNDRED, new BigDecimal("1.00"),false);

		Assertions.assertThrows(NotYetImplementedException.class,
				() -> service.transfer(JOHN_DOE, JANE_DOE, new BigDecimal("50.00")));
	}

	private AccountDTO newAccount(String name, String currency, String amount, BigDecimal mid,  Boolean treasury) throws AccountAlreadyExistsException {
		AccountDTO account = new AccountDTO();
		account.setCurrency(new Currency(currency ,currency, mid));
		account.setName(name);
		account.setMoney(new Money(new BigDecimal(amount)));
		account.setTreasury(treasury);

		return service.create(account);
	}
}
