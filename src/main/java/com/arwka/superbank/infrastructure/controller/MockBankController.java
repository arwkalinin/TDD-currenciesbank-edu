package com.arwka.superbank.infrastructure.controller;

import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.impl.MockBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import com.arwka.superbank.application.service.impl.MockBankService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MockBankController {

  private final MockBankRepository mockBank;
  private final MockBankService mockBankService;

  // ----------------------------

  /**
   * Init currencies for Mock Bank.
   */
  @PostConstruct
  private void init() {
    mockBank.setCurrenciesMap(mockBankService.actualizeAndGetCurrencies());
  }

  // ----------------------------

  /**
   * Get current currencies from MockBank.
   *
   * @return HashMap<Pair, BigDecimal>
   */
  @GetMapping("mock-bank/currencies")
  public ResponseEntity<HashMap<Pair, BigDecimal>> getCurrenciesOfMockBank() {
    return ResponseEntity
        .ok()
        .body(mockBank.getCurrenciesMap());
  }

  /**
   * Convert currency with actual currencies from Mock Bank.
   *
   * @param amount - amount of FROM currency
   * @param from   - currency FROM convert
   * @param to     - currency TO convert
   * @return BigDecimal
   */
  @GetMapping("mock-bank/convert")
  public ResponseEntity<Money> convert(
      @RequestParam(name = "amount") BigDecimal amount,
      @RequestParam(name = "from") String from,
      @RequestParam(name = "to") String to) {

    Currency fromCurrency = Currency.valueOf(from.toUpperCase());
    Currency toCurrency = Currency.valueOf(to.toUpperCase());
    Money fromMoney = Money.of(amount, fromCurrency);

    return ResponseEntity
        .ok()
        .body(mockBankService.convert(toCurrency, fromMoney, mockBank));
  }

  /**
   * Update currency in Mock Bank.
   *
   * @param rate - new rate for currencies pair
   * @param from - from currency (FOR N *currency* ...)
   * @param to   - to currency (... GIVE N *currency*)
   */
  @PostMapping("mock-bank/currencies/update-rate")
  public ResponseEntity<List<String>> updateRate(
      @RequestParam(name = "rate") BigDecimal rate,
      @RequestParam(name = "from") String from,
      @RequestParam(name = "to") String to) {

    Currency fromCurrency = Currency.valueOf(from.toUpperCase());
    Currency toCurrency = Currency.valueOf(to.toUpperCase());

    mockBank.setRate(fromCurrency, toCurrency, rate);

    return ResponseEntity
        .status(HttpStatus.SC_ACCEPTED)
        .body(List.of("success", rate.toString()));
  }

}
