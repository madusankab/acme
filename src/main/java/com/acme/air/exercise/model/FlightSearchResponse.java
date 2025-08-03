package com.acme.air.exercise.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightSearchResponse {

  private List<FlightInstanceDTO> flightInstances;
}
