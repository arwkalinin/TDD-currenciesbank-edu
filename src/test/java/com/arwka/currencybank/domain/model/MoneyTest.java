package com.arwka.currencybank.domain.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MoneyTest {

  @Test
  @DisplayName("Money with -2 amount should throw IllegalArgumentException")
  void negativeMoney() {
    assertThrows(IllegalArgumentException.class,
        () -> Money.of(BigDecimal.valueOf(-2), Currency.RUB), "Amount of Money should be > 0");
  }

  @Test
  @DisplayName("Plus 1 EUR and 1 USD should throw IllegalArgumentException")
  void plusDiffCurrenciesTest() {
    assertThrows(IllegalArgumentException.class,
        () -> Money.of(BigDecimal.ONE, Currency.USD).plus(Money.of(BigDecimal.ONE, Currency.RUB)));
  }

  @Test
  @DisplayName("Times 2 EUR by -2 should throw IllegalArgumentException")
  void timesNegativeTest() {
    assertThrows(IllegalArgumentException.class,
        () -> Money.of(BigDecimal.ONE, Currency.RUB).times(-2), "Multiplier should be > 0");
  }

  @Test
  @DisplayName("Money with same amount and currency should be equals")
  void selfEquality() {
    assertAll(
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.USD),
            Money.of(BigDecimal.ONE, Currency.USD)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.EUR),
            Money.of(BigDecimal.ONE, Currency.EUR)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.CHF),
            Money.of(BigDecimal.ONE, Currency.CHF)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.RUB),
            Money.of(BigDecimal.ONE, Currency.RUB))
    );
  }

  @Test
  @DisplayName("Money with different currencies should NOT be equals")
  void notEqualityWithDiffCurrencies() {
    assertAll(
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.USD),
            Money.of(BigDecimal.ONE, Currency.CHF)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.EUR),
            Money.of(BigDecimal.ONE, Currency.RUB)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.CHF),
            Money.of(BigDecimal.ONE, Currency.EUR)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.RUB),
            Money.of(BigDecimal.ONE, Currency.USD))
    );
  }

  @Test
  @DisplayName("Money with different amount and same currencies should NOT be equals")
  void notEqualityWithDiffAmount() {
    assertAll(
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.USD),
            Money.of(BigDecimal.TEN, Currency.USD)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.EUR),
            Money.of(BigDecimal.TEN, Currency.EUR)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.CHF),
            Money.of(BigDecimal.TEN, Currency.CHF)),
        () -> assertNotEquals(
            Money.of(BigDecimal.ONE, Currency.RUB),
            Money.of(BigDecimal.TEN, Currency.RUB))
    );
  }

  @Test
  @DisplayName("Plus 1 XXX and 1 XXX should be 2 XXX")
  void plusTest() {
    assertAll(
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.USD).plus(Money.of(BigDecimal.ONE, Currency.USD)),
            Money.of(BigDecimal.valueOf(2), Currency.USD)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.EUR).plus(Money.of(BigDecimal.ONE, Currency.EUR)),
            Money.of(BigDecimal.valueOf(2), Currency.EUR)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.RUB).plus(Money.of(BigDecimal.ONE, Currency.RUB)),
            Money.of(BigDecimal.valueOf(2), Currency.RUB)),
        () -> assertEquals(
            Money.of(BigDecimal.ONE, Currency.CHF).plus(Money.of(BigDecimal.ONE, Currency.CHF)),
            Money.of(BigDecimal.valueOf(2), Currency.CHF))
    );
  }

  @Test
  @DisplayName("Plus 0 XXX and 5.7 XXX should be 5.7 XXX")
  void plusSecondTest() {
    assertAll(
        () -> assertEquals(
            Money.of(BigDecimal.ZERO, Currency.USD)
                .plus(Money.of(BigDecimal.valueOf(5.7), Currency.USD)),
            Money.of(BigDecimal.valueOf(5.7), Currency.USD)),
        () -> assertEquals(
            Money.of(BigDecimal.ZERO, Currency.EUR)
                .plus(Money.of(BigDecimal.valueOf(5.7), Currency.EUR)),
            Money.of(BigDecimal.valueOf(5.7), Currency.EUR)),
        () -> assertEquals(
            Money.of(BigDecimal.ZERO, Currency.RUB)
                .plus(Money.of(BigDecimal.valueOf(5.7), Currency.RUB)),
            Money.of(BigDecimal.valueOf(5.7), Currency.RUB)),
        () -> assertEquals(
            Money.of(BigDecimal.ZERO, Currency.CHF)
                .plus(Money.of(BigDecimal.valueOf(5.7), Currency.CHF)),
            Money.of(BigDecimal.valueOf(5.7), Currency.CHF))
    );
  }

  @Test
  @DisplayName("Times 2 USD by 2 should be 4 USD")
  void timesTest() {
    assertEquals(
        Money.of(BigDecimal.valueOf(2), Currency.USD).times(2),
        Money.of(BigDecimal.valueOf(4.0D), Currency.USD)
    );
  }

  @Test
  @DisplayName("Times 10 CHF by 0 should be 0 CHF")
  void timesByZeroTest() {
    assertEquals(
        Money.of(BigDecimal.TEN, Currency.CHF).times(0),
        Money.of(BigDecimal.valueOf(0.0D), Currency.CHF)
    );
  }

  @Test
  @DisplayName("GetAmount with 10 RUB should be 10 RUB")
  void getAmountTest() {
    assertEquals(
        Money.of(BigDecimal.TEN, Currency.RUB)
            .getAmount(),
        BigDecimal.valueOf(10)
    );
  }

}
