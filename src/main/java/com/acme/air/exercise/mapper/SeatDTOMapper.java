package com.acme.air.exercise.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.model.SeatDTO;

@Component
public class SeatDTOMapper {

  public List<SeatDTO> map(final List<Seat> seats) {
    return CollectionUtils.isEmpty(seats) ? Collections.emptyList()
        : seats.stream().map(this::map).toList();
  }

  public SeatDTO map(final Seat seat) {
    SeatDTO dto = null;

    if (Objects.nonNull(seat)) {
      dto = new SeatDTO();
      dto.setSeatNumber(seat.getSeatNumber());
      dto.setSeatType(seat.getSeatType());
      dto.setBooked(seat.isBooked());
    }

    return dto;
  }

}
