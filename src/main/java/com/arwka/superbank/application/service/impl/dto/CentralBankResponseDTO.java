package com.arwka.superbank.application.service.impl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Getter;

@Getter
public class CentralBankResponseDTO {

  @JsonProperty("Valute")
  private Map<String, Valute> valute;

  @Getter
  public static class Valute {
    @JsonProperty("CharCode")
    private String charCode;
    @JsonProperty("Nominal")
    private int nominal;
    @JsonProperty("Value")
    private BigDecimal value;
  }

}