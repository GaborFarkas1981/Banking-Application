package com.gfarkas.banking.service;

import com.gfarkas.banking.dto.AccountDTO;
import com.gfarkas.banking.exception.AccountAlreadyExistsException;
import com.gfarkas.banking.exception.AccountNotFoundException;
import com.gfarkas.banking.exception.InSufficientFundException;
import com.gfarkas.banking.mapper.AccountMapper;
import com.gfarkas.banking.model.AccountEntity;
import com.gfarkas.banking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    @Override
    public AccountDTO findByName(String name) throws AccountNotFoundException {

        AccountEntity entity = repository.findByName(name);
        if (entity == null) throw new AccountNotFoundException("Account not found!");

        return mapper.entityToDto(entity);
    }

    @Override
    @Transactional
    public AccountDTO update(AccountDTO dto, String name) throws AccountNotFoundException {

        AccountEntity existingAccount = repository.findByName(name);
        if (existingAccount == null) throw new AccountNotFoundException("Account not found!");

        AccountEntity newEntity = mapper.dtoToEntity(dto);
        newEntity.setTreasury(existingAccount.getTreasury()); // Transfer Treasury information from existing account
        newEntity.setId(existingAccount.getId());
        newEntity = repository.save(newEntity);

        return mapper.entityToDto(newEntity);
    }

    @Override
    @Transactional
    public AccountDTO create(AccountDTO dto) throws AccountAlreadyExistsException {

        AccountEntity existingAccount = repository.findByName(dto.getName());
        if (existingAccount != null) throw new AccountAlreadyExistsException("Account already exists!");
        if (dto.getTreasury() == null) dto.setTreasury(false);

        repository.save(mapper.dtoToEntity(dto));

        return dto;
    }


    @Override
    public AccountDTO delete(AccountDTO dto) throws AccountNotFoundException {
        AccountEntity existingAccount = repository.findByName(dto.getName());
        if (existingAccount == null) throw new AccountNotFoundException("Account not found!");

        repository.delete(existingAccount);

        return mapper.entityToDto(existingAccount);
    }

    @Override
    @Transactional
    public void transfer(String fromName, String toName, BigDecimal amount) throws AccountNotFoundException {

        // Check that both accounts exist
        AccountDTO from = findByName(fromName);
        AccountDTO to = findByName(toName);

        // TODO Implement currency conversion
        if (!from.getCurrency().getCode().equals(to.getCurrency().getCode())) {
            throw new NotYetImplementedException("Transfer between different currencies not yet implemented");
        }

        if (from.getMoney().getAmount().compareTo(amount) >= 0) {
            to.getMoney().setAmount(to.getMoney().getAmount().add(amount));
            from.getMoney().setAmount(from.getMoney().getAmount().subtract(amount));

            update(from, from.getName());
            update(to, to.getName());
        } else {
            throw new InSufficientFundException("There is not enough money to transfer!");
        }
    }

}
