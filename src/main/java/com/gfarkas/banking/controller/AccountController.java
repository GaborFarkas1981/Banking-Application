package com.gfarkas.banking.controller;

import com.gfarkas.banking.dto.AccountDTO;
import com.gfarkas.banking.exception.AccountAlreadyExistsException;
import com.gfarkas.banking.exception.AccountNotFoundException;
import com.gfarkas.banking.service.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountServiceImpl service;

    @GetMapping(value = "/accounts/{name}")
    ResponseEntity<AccountDTO> getByName(
            @PathVariable("name") String name) throws AccountNotFoundException {
        AccountDTO dto = service.findByName(name);
        if (dto == null) throw new AccountNotFoundException("No Account with name: " + name);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public AccountDTO createAccount(@RequestBody AccountDTO dto) throws AccountNotFoundException, AccountAlreadyExistsException {

        return service.create(dto);
    }

    @PutMapping(value = "/accounts/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public AccountDTO updateAccount(@PathVariable("name") String name, @RequestBody AccountDTO dto) throws AccountNotFoundException {

        return service.update(dto, name);
    }

    @PutMapping(value = "/accounts/{nameFrom}/{nameTo}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void transferMoney(
            @PathVariable("nameFrom") String nameFrom,
            @PathVariable("nameFrom") String nameTo,
            @PathVariable("amount") BigDecimal amount,
            @RequestBody AccountDTO dto) throws AccountNotFoundException {

        service.transfer(nameFrom, nameTo, amount);
    }


    @DeleteMapping(value = "/accounts/{name}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDTO deleteAccount(@PathVariable("name") String name) throws AccountNotFoundException {
        AccountDTO dto = service.findByName(name);

        return service.delete(dto);
    }

}
