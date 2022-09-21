package com.arwka.currencybank.infrastructure.persistent.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.infrastructure.persistent.repository.CurrenciesBankRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CentralBankRepositoryTest {

  @Test
  @DisplayName("setPair with rate -5 should throw IllegalArgumentException with message")
  void setPair_negativeRate() {
    assertThrows(IllegalArgumentException.class, () -> {
      CurrenciesBankRepository bank = new CentralBankRepository();
      bank.setRate(Currency.USD, Currency.CHF, BigDecimal.valueOf(-5));
    }, "rate cannot be <= 0");
  }

  @Test
  @DisplayName("rateOf with currenciesMap USD->RUB(60) should return 60")
  void rateOfTest() {
    CurrenciesBankRepository bank = new CentralBankRepository();
    bank.setRate(Currency.USD, Currency.RUB, BigDecimal.valueOf(60));

    assertEquals(
        bank.rateOf(Currency.USD, Currency.RUB),
        BigDecimal.valueOf(60)
    );
  }

}
