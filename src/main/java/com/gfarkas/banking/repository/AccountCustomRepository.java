package com.gfarkas.banking.repository;


import com.gfarkas.banking.model.AccountEntity;

public interface AccountCustomRepository {
    AccountEntity findByName(String name);
}
