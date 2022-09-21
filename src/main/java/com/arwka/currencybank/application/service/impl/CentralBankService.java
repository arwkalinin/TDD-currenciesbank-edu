package com.arwka.currencybank.application.service.impl;

import com.arwka.currencybank.application.service.CurrenciesBankService;
import com.arwka.currencybank.application.service.impl.dto.CentralBankResponseDTO;
import com.arwka.currencybank.application.service.impl.dto.CentralBankResponseDTO.Valute;
import com.arwka.currencybank.domain.model.Money;
import com.arwka.currencybank.infrastructure.persistent.repository.CurrenciesBankRepository;
import com.arwka.currencybank.infrastructure.persistent.repository.impl.CentralBankRepository;
import com.arwka.currencybank.domain.model.Currency;
import com.arwka.currencybank.domain.model.Pair;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CentralBankService implements CurrenciesBankService {

  final CentralBankRepository centralBankRepository;

  // -----------------------------------

  @Value("${central-bank-client.url}")
  private String url;
  @Value("${central-bank-client.connection-timeout}")
  private long connectionTimeoutMs;
  @Value("${central-bank-client.read-timeout}")
  private long readTimeoutMs;
  private final RestTemplate restTemplate;

  // -----------------------------------

  @Autowired
  public CentralBankService(
      RestTemplateBuilder restTemplateBuilder, CentralBankRepository centralBankRepository) {

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));

    this.restTemplate = restTemplateBuilder
        .messageConverters(converter)
        .setConnectTimeout(Duration.of(connectionTimeoutMs, ChronoUnit.MILLIS))
        .setReadTimeout(Duration.of(readTimeoutMs, ChronoUnit.MILLIS))
        .build();

    this.centralBankRepository = centralBankRepository;
  }

  // -----------------------------------

  @Override
  public Money convert(Currency to, Money money, CurrenciesBankRepository bank) {
    BigDecimal convertRate = bank.rateOf(money.getCurrency(), to);
    return Money.of(money.getAmount().multiply(convertRate), to);
  }

  // -----------------------------------

  @Override
  public HashMap<Pair, BigDecimal> actualizeAndGetCurrencies() {

    final ResponseEntity<CentralBankResponseDTO> response = callCBClientForCurrenciesJSon();

    checkResponse(response);

    List<Valute> listOfValute = new ArrayList<>();
    for (Currency value : Currency.values()) {
      listOfValute.add(
          Objects.requireNonNull(response.getBody()).getValute().get(value.toString()));
    }

    HashMap<Pair, BigDecimal> actualizedCurrencies = new HashMap<>();

    listOfValute.forEach((valute) -> {
      if (valute != null) {
        actualizedCurrencies.put(
            Pair.of(Currency.valueOf(valute.getCharCode()), Currency.RUB),
            valute.getValue().divide(BigDecimal.valueOf(valute.getNominal()), 4, RoundingMode.UP)
        );
        actualizedCurrencies.put(
            Pair.of(Currency.RUB, Currency.valueOf(valute.getCharCode())),
            (BigDecimal.valueOf(valute.getNominal())).divide(valute.getValue(), 4, RoundingMode.UP)
        );
      }
    });

    return actualizedCurrencies;
  }

  private ResponseEntity<CentralBankResponseDTO> callCBClientForCurrenciesJSon() {
    return restTemplate.exchange(
        url,
        HttpMethod.GET,
        null,
        CentralBankResponseDTO.class
    );
  }

  private void checkResponse(final ResponseEntity<CentralBankResponseDTO> response) {
    if (!response.getStatusCode().is2xxSuccessful()) {
      try {
        throw new Exception(response.getStatusCode().getReasonPhrase());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    if (response.getBody() == null) {
      try {
        throw new Exception("empty body");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

}
