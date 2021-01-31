package com.gfarkas.banking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    private String currency;
    private String code;
    private BigDecimal mid;

}
