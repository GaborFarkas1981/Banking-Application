package com.gfarkas.banking.service;

import com.gfarkas.banking.dto.AccountDTO;
import com.gfarkas.banking.exception.AccountAlreadyExistsException;
import com.gfarkas.banking.exception.AccountNotFoundException;

import java.math.BigDecimal;

public interface AccountService {
    AccountDTO findByName(String name) throws AccountNotFoundException;
    AccountDTO create(AccountDTO account) throws AccountAlreadyExistsException;
    AccountDTO update(AccountDTO account, String name) throws AccountNotFoundException;
    AccountDTO delete(AccountDTO dto) throws AccountNotFoundException;
    void transfer(String nameFrom, String nameTo, BigDecimal amount) throws AccountNotFoundException;

    }
