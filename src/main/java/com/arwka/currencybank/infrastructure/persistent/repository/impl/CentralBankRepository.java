package com.arwka.currencybank.infrastructure.persistent.repository.impl;

import com.arwka.currencybank.infrastructure.persistent.repository.CurrenciesBankRepository;
import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.domain.model.Pair;
import java.math.BigDecimal;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Getter
@Setter
@Repository
public class CentralBankRepository implements CurrenciesBankRepository {

  private HashMap<Pair, BigDecimal> currenciesMap = new HashMap<>();

  /**
   * Update currency in Mock Bank.
   *
   * @param from - from currency (FOR N *currency* ...)
   * @param to   - to currency (... GIVE N *currency*)
   * @return BigDecimal
   */
  @Override
  public BigDecimal rateOf(Currency from, Currency to) {
    if (from.equals(to)) {
      return BigDecimal.valueOf(1);
    } else {
      return currenciesMap.get(Pair.of(from, to));
    }
  }

  /**
   * Update currency.
   *
   * @param from - from currency (FOR N *currency* ...)
   * @param to   - to currency (... GIVE N *currency*)
   * @param rate - new rate for currencies pair
   */
  @Override
  public void setRate(Currency from, Currency to, BigDecimal rate) {
    if (rate.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("rate cannot be <= 0");
    } else {
      currenciesMap.put(Pair.of(from, to), rate);
    }
  }
}
