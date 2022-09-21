package com.arwka.currencybank.domain.model;

import lombok.EqualsAndHashCode;

/**
 * Currency pair from->to
 */
@EqualsAndHashCode
public final class Pair {

  private final Currency from;
  private final Currency to;

  /**
   * Factory method.
   *
   * @param from - from currency
   * @param to   - to currency
   * @return Pair
   */
  public static Pair of(Currency from, Currency to) {
    return new Pair(from, to);
  }

  private Pair(Currency from, Currency to) {
    this.from = from;
    this.to = to;
  }

  @Override
  public String toString() {
    return from + "->" + to;
  }
}
