package com.gfarkas.banking.repository;

import com.gfarkas.banking.model.AccountEntity;
import com.gfarkas.banking.model.QAccountEntity;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @PersistenceContext()
    EntityManager em;

    @Override
    public AccountEntity findByName(String name) {
        QAccountEntity account = QAccountEntity.accountEntity;
        JPAQuery<AccountEntity> query = new JPAQuery<>(em);

        return query.select(account).from(account).where(account.name.eq(name)).fetchOne();
    }

}
