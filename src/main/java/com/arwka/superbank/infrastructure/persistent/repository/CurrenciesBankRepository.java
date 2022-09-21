package com.arwka.superbank.infrastructure.persistent.repository;

import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrenciesBankRepository {
  BigDecimal rateOf(Currency from, Currency to);
  void setRate(Currency from, Currency to, BigDecimal rate);
  HashMap<Pair, BigDecimal> getCurrenciesMap();
  void setCurrenciesMap(HashMap<Pair, BigDecimal> hashMap);
}
