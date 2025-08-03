package com.acme.air.exercise.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.acme.air.exercise.entity.FlightInstanceStatus;

import lombok.Data;

@Data
public class FlightInstanceDTO {

  private Long id;

  private LocalDate departureDate;

  private LocalTime departureTime;

  private LocalTime arrivalTime;

  private FlightInstanceStatus status;

  private List<SeatDTO> seats;
}
