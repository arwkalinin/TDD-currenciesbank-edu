package com.arwka.currencybank.application.impl;

import com.arwka.currencybank.application.service.impl.CentralBankService;
import com.arwka.currencybank.domain.model.Money;
import com.arwka.currencybank.infrastructure.persistent.repository.impl.CentralBankRepository;
import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CentralBankServiceTest {

  @Autowired
  CentralBankService centralBankService;

  @Autowired
  CentralBankRepository centralBankRepository;

  @Test
  @DisplayName("getActualCurrencies return correct hashMap")
  void getActualCurrencies() {
    HashMap<Pair, BigDecimal> currenciesMap = centralBankService.actualizeAndGetCurrencies();
    Assertions.assertAll(
        () -> Assertions.assertEquals(66, currenciesMap.size()),
        () -> Assertions.assertNotNull(currenciesMap.get(Pair.of(Currency.RUB, Currency.USD))),
        () -> Assertions.assertNotNull(currenciesMap.get(Pair.of(Currency.JPY, Currency.RUB)))
    );
  }

  @Test
  @DisplayName("Convert 2 USD to RUB should return 120 RUB")
  void convertTest() {
    centralBankRepository.setRate(Currency.USD, Currency.RUB, BigDecimal.valueOf(60.7D));
    Assertions.assertEquals(
        Money.of(BigDecimal.valueOf(121.4D), Currency.RUB),
        centralBankService
            .convert(Currency.RUB, Money.of(BigDecimal.valueOf(2), Currency.USD), centralBankRepository)
    );
  }

}
