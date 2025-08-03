package com.acme.air.exercise.model;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;

@Data
public class FlightDTO {

  private String flightId;

  private String origin;

  private String destination;

  private LocalTime departureTime;

  private LocalTime arrivalTime;

  private List<FlightInstanceDTO> flightInstances;

}
