package com.arwka.superbank.application.service.impl;

import com.arwka.superbank.application.service.CurrenciesBankService;
import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.CurrenciesBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class MockBankService implements CurrenciesBankService {

  @Override
  public HashMap<Pair, BigDecimal> actualizeAndGetCurrencies() {

    HashMap<Pair, BigDecimal> mockCurrencies = new HashMap<>(6, 1);
    mockCurrencies.put(Pair.of(Currency.EUR, Currency.USD), BigDecimal.valueOf(2));
    mockCurrencies.put(Pair.of(Currency.EUR, Currency.CHF), BigDecimal.valueOf(0.5d));
    mockCurrencies.put(Pair.of(Currency.USD, Currency.EUR), BigDecimal.valueOf(0.5d));
    mockCurrencies.put(Pair.of(Currency.USD, Currency.CHF), BigDecimal.valueOf(0.25d));
    mockCurrencies.put(Pair.of(Currency.CHF, Currency.USD), BigDecimal.valueOf(4));
    mockCurrencies.put(Pair.of(Currency.CHF, Currency.EUR), BigDecimal.valueOf(2));

    return mockCurrencies;
  }

  @Override
  public Money convert(Currency to, Money money, CurrenciesBankRepository bank) {
    BigDecimal convertRate = bank.rateOf(money.getCurrency(), to);
    return Money.of(money.getAmount().multiply(convertRate), to);
  }

}
