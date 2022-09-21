package com.arwka.superbank.application.service;

import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.CurrenciesBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrenciesBankService {

  HashMap<Pair, BigDecimal> actualizeAndGetCurrencies();
  Money convert(Currency to, Money money, CurrenciesBankRepository bank);

}
