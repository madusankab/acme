package com.acme.air.exercise.model;

import lombok.Data;

@Data
public class FlightBookingResponse {

  private SeatDTO seat;

  private FlightBookingStatus status;
}
