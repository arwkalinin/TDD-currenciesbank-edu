package com.arwka.superbank.infrastructure.controller;

import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.impl.CentralBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import com.arwka.superbank.application.service.impl.CentralBankService;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CentralBankController {

  private final CentralBankRepository centralBank;
  private final CentralBankService centralBankService;

  // ----------------------------

  /**
   * Actualize currencies from CentralBank on initializing CentralBankController.
   */
  @PostConstruct
  private void init() {
    centralBank.setCurrenciesMap(centralBankService.actualizeAndGetCurrencies());
  }

  // ----------------------------

  /**
   * Get current currencies from CentralBank.
   *
   * @return HashMap<Pair, BigDecimal>
   */
  @GetMapping("cb/currencies")
  public ResponseEntity<HashMap<Pair, BigDecimal>> getCurrenciesOfCB() {
    return ResponseEntity
        .ok()
        .body(centralBank.getCurrenciesMap());
  }

  /**
   * Convert currency with actual currencies from Central Bank.
   *
   * @param amount - amount of FROM currency
   * @param from   - currency FROM convert
   * @param to     - currency TO convert
   * @return BigDecimal
   */
  @GetMapping("cb/convert")
  public ResponseEntity<Money> convert(
      @RequestParam(name = "amount") BigDecimal amount,
      @RequestParam(name = "from") String from,
      @RequestParam(name = "to") String to) {

    Currency fromCurrency = Currency.valueOf(from.toUpperCase());
    Currency toCurrency = Currency.valueOf(to.toUpperCase());

    Money fromMoney = Money.of(amount, fromCurrency);

    if (!from.equals("RUB")) {
      fromMoney = centralBankService.convert(Currency.RUB, fromMoney, centralBank);
    }

    return ResponseEntity
        .ok()
        .body(centralBankService.convert(toCurrency, fromMoney, centralBank));
  }

}
