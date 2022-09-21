package com.arwka.currencybank.infrastructure.persistent.repository;

import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrenciesBankRepository {
  BigDecimal rateOf(Currency from, Currency to);
  void setRate(Currency from, Currency to, BigDecimal rate);
  HashMap<Pair, BigDecimal> getCurrenciesMap();
  void setCurrenciesMap(HashMap<Pair, BigDecimal> hashMap);
}
