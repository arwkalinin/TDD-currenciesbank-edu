package com.arwka.superbank.infrastructure.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.impl.CentralBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import com.arwka.superbank.application.service.impl.CentralBankService;
import java.math.BigDecimal;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



@SpringBootTest
@AutoConfigureMockMvc
public class CentralBankControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CentralBankService centralBankService;

  @MockBean
  private CentralBankRepository centralBankRepository;

  @Test
  @DisplayName("GET /cb/currencies")
  void getCurrenciesTest() throws Exception {

    final HashMap<Pair, BigDecimal> mockCurrencies = new HashMap<>();
    mockCurrencies.put(Pair.of(Currency.USD, Currency.RUB), BigDecimal.ONE);
    mockCurrencies.put(Pair.of(Currency.EUR, Currency.RUB), BigDecimal.TEN);

    doReturn(mockCurrencies)
        .when(centralBankRepository).getCurrenciesMap();

    mockMvc.perform(get("/cb/currencies"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$['USD->RUB']", is(1)))
        .andExpect(jsonPath("$['EUR->RUB']", is(10)));
  }

  @Test
  @DisplayName("GET /cb/convert?amount=1&from=RUB&to=JPY")
  void convertTest() throws Exception {

    Money responseMoney = Money.of(BigDecimal.valueOf(2.2033), Currency.JPY);

    doReturn(responseMoney)
        .when(centralBankService)
        .convert(Currency.JPY, Money.of(BigDecimal.ONE, Currency.RUB), centralBankRepository);

    mockMvc.perform(get("/cb/convert?amount=1&from=RUB&to=JPY"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$['amount']", is(2.2033)))
        .andExpect(jsonPath("$['currency']", is("JPY")));
  }

}
