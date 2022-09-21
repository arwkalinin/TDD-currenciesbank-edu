package com.arwka.superbank.infrastructure.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arwka.superbank.domain.model.Money;
import com.arwka.superbank.infrastructure.persistent.repository.impl.MockBankRepository;
import com.arwka.superbank.domain.model.Currency;
import com.arwka.superbank.domain.model.Pair;
import com.arwka.superbank.application.service.impl.MockBankService;
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
public class MockBankControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MockBankRepository mockBankRepository;

  @MockBean
  private MockBankService mockBankService;

  @Test
  @DisplayName("GET /mock-bank/currencies")
  void getCurrenciesTest() throws Exception {

    final HashMap<Pair, BigDecimal> mockCurrencies = new HashMap<>();
    mockCurrencies.put(Pair.of(Currency.USD, Currency.RUB), BigDecimal.ONE);
    mockCurrencies.put(Pair.of(Currency.RUB, Currency.CHF), BigDecimal.valueOf(0.5D));

    doReturn(mockCurrencies)
        .when(mockBankRepository).getCurrenciesMap();

    mockMvc.perform(get("/mock-bank/currencies"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$['USD->RUB']", is(1)))
        .andExpect(jsonPath("$['RUB->CHF']", is(0.5D)));
  }

  @Test
  @DisplayName("GET /mock-bank/convert?amount=10&from=USD&to=CHF")
  void convertTest() throws Exception {

    Money responseMoney = Money.of(BigDecimal.valueOf(12), Currency.CHF);

    doReturn(responseMoney)
        .when(mockBankService)
        .convert(Currency.CHF, Money.of(BigDecimal.TEN, Currency.USD), mockBankRepository);

    mockMvc.perform(get("/mock-bank/convert?amount=10&from=USD&to=CHF"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$['amount']", is(12)))
        .andExpect(jsonPath("$['currency']", is("CHF")));
  }

  @Test
  @DisplayName("POST /mock-bank/currencies/update-rate?rate=20&from=USD&to=RUB")
  void updateRateTest() throws Exception {

    mockMvc.perform(post("/mock-bank/currencies/update-rate?rate=20&from=USD&to=RUB"))
        .andExpect(status().is2xxSuccessful());
  }

}
