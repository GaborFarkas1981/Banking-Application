package com.gfarkas.banking.dto;

import com.gfarkas.banking.model.Currency;
import com.gfarkas.banking.model.Money;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class AccountDTO {
    private String name;
    private Currency currency;
    private Money money;
    private Boolean treasury;

}
