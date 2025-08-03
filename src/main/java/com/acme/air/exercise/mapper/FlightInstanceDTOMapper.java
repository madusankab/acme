package com.acme.air.exercise.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.model.FlightInstanceDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FlightInstanceDTOMapper {

  private final SeatDTOMapper seatDTOMapper;

  public List<FlightInstanceDTO> map(final List<FlightInstance> flightInstances) {
    return CollectionUtils.isEmpty(flightInstances) ? Collections.emptyList() :
        flightInstances.stream().map(this::map).toList();
  }

  private FlightInstanceDTO map(final FlightInstance flightInstance) {
    FlightInstanceDTO dto = null;

    if (Objects.nonNull(flightInstance)) {
      dto = new FlightInstanceDTO();
      dto.setId(flightInstance.getId());
      dto.setDepartureDate(flightInstance.getDepartureDate());
      dto.setDepartureTime(flightInstance.getDepartureTime());
      dto.setArrivalTime(flightInstance.getArrivalTime());
      dto.setStatus(flightInstance.getStatus());
      dto.setSeats(seatDTOMapper.map(flightInstance.getSeats()));
    }

    return dto;
  }

}
