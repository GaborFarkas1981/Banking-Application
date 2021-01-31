package com.gfarkas.banking.mapper;

import com.gfarkas.banking.dto.AccountDTO;
import com.gfarkas.banking.model.AccountEntity;
import com.gfarkas.banking.model.Currency;
import com.gfarkas.banking.model.Money;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public AccountEntity dtoToEntity(AccountDTO data) {
        AccountEntity entity = new AccountEntity();
        entity.setCurrency(new Currency());
        entity.setMoney(new Money());
        entity.setTreasury(data.getTreasury());
        entity.getCurrency().setCurrency(data.getCurrency().getCurrency());
        entity.getCurrency().setCode(data.getCurrency().getCode());
        entity.getCurrency().setMid(data.getCurrency().getMid());
        entity.setName(data.getName());
        entity.getMoney().setAmount(data.getMoney().getAmount());

        return entity;
    }

    public AccountDTO entityToDto(AccountEntity entity) {
        AccountDTO dto = new AccountDTO();
        dto.setCurrency(new Currency());
        dto.setMoney(new Money());
        dto.setTreasury(entity.getTreasury());
        dto.getCurrency().setCurrency(entity.getCurrency().getCurrency());
        dto.getCurrency().setCode(entity.getCurrency().getCode());
        dto.getCurrency().setMid(entity.getCurrency().getMid());
        dto.setName(entity.getName());
        dto.getMoney().setAmount(entity.getMoney().getAmount());

        return dto;
    }
}
