package com.arwka.superbank.application.impl;

import com.arwka.superbank.application.service.impl.MockBankService;
import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.impl.MockBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MockBankServiceTest {

  @Autowired
  MockBankService mockBankService;

  @Autowired
  MockBankRepository mockBankRepository;

  @Test
  @DisplayName("getActualCurrencies should return correct currencies map")
  void getActualCurrencies() {

    HashMap<Pair, BigDecimal> currenciesMap = mockBankService.actualizeAndGetCurrencies();

    Assertions.assertAll(
        () -> Assertions.assertEquals(6, currenciesMap.size()),
        () -> Assertions.assertNotNull(currenciesMap.get(Pair.of(Currency.USD, Currency.EUR))),
        () -> Assertions.assertNotNull(currenciesMap.get(Pair.of(Currency.EUR, Currency.USD))),
        () -> Assertions.assertNotNull(currenciesMap.get(Pair.of(Currency.CHF, Currency.USD)))
    );
  }

  @Test
  @DisplayName("Convert 2 USD to EUR should return 1 EUR")
  void convertTest() {
    Assertions.assertEquals(
        Money.of(BigDecimal.valueOf(1.0D), Currency.EUR),
        mockBankService
            .convert(Currency.EUR, Money.of(BigDecimal.valueOf(2), Currency.USD), mockBankRepository)
    );
  }

}
