package com.gfarkas.banking.repository;

import com.gfarkas.banking.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>, AccountCustomRepository {
}
