package com.arwka.superbank.domain.model;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Money {

  private final BigDecimal amount;
  private final Currency currency;

  // ------------------------

  /**
   * Factory method.
   *
   * @param amountOfMoney - amount of money
   * @param currency      - currency
   * @return Money
   */
  public static Money of(BigDecimal amountOfMoney, Currency currency) {
    return new Money(amountOfMoney, currency);
  }

  private Money(BigDecimal amount, Currency currency) {
    if (isValid(amount)) {
      this.amount = amount;
      this.currency = currency;
    } else {
      throw new IllegalArgumentException("Amount of Money should be > 0");
    }
  }

  private boolean isValid(BigDecimal amount) {
    return amount.compareTo(BigDecimal.ZERO) >= 0;
  }

  // ------------------------

  /**
   * Addition of two currencies.
   *
   * @param addend - Money to addend to this
   * @return Money
   * @throws IllegalArgumentException if currencies of Money are different
   */
  public Money plus(Money addend) {
    if (currency == addend.currency) {
      return Money.of(amount.add(addend.getAmount()), currency);
    } else {
      throw new IllegalArgumentException("Currencies of Money are different.");
    }

  }

  /**
   * Multiplies this amount by multiplier and return new Money
   *
   * @param multiplier - multiplier
   * @return Money
   * @throws IllegalArgumentException if multiplier is < 0
   */
  public Money times(double multiplier) {
    if (multiplier >= 0) {
      return Money.of(amount.multiply(BigDecimal.valueOf(multiplier)), currency);
    } else {
      throw new IllegalArgumentException("Multiplier should be > 0");
    }
  }

}
