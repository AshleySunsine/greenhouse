package ru.ash.greenhouse.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BankAccountEntity {
    @Id
    String numberAccount;

    Currency currency;

    BigDecimal balance;
}