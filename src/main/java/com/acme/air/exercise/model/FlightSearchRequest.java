package com.acme.air.exercise.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FlightSearchRequest {

  @NotBlank
  private String origin;

  @NotBlank
  private String destination;

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate departureDate;

  @Positive
  private int numOfSeats;

}
