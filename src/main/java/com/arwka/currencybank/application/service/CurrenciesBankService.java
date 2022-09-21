package com.arwka.currencybank.application.service;

import com.arwka.currencybank.domain.model.Money;
import com.arwka.currencybank.infrastructure.persistent.repository.CurrenciesBankRepository;
import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrenciesBankService {

  HashMap<Pair, BigDecimal> actualizeAndGetCurrencies();
  Money convert(Currency to, Money money, CurrenciesBankRepository bank);

}
