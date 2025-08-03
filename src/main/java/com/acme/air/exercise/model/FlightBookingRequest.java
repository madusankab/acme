package com.acme.air.exercise.model;

import com.acme.air.exercise.entity.SeatType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightBookingRequest {

  @NotNull
  private Long flightInstanceId;

  @NotNull
  private SeatType seatType;
}
