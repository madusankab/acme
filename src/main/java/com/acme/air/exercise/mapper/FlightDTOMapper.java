package com.acme.air.exercise.mapper;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.model.FlightDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightDTOMapper {

  public FlightDTO map(final Flight flight) {
    FlightDTO dto = null;

    if (Objects.nonNull(flight)) {
      dto = new FlightDTO();
      dto.setFlightId(flight.getFlightId());
      dto.setOrigin(flight.getOrigin());
      dto.setDestination(flight.getDestination());
      dto.setDepartureTime(flight.getDepartureTime());
      dto.setArrivalTime(flight.getArrivalTime());
    }

    return dto;
  }

}
