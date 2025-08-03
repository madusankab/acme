package com.acme.air.exercise.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.mapper.SeatDTOMapper;
import com.acme.air.exercise.model.FlightBookingRequest;
import com.acme.air.exercise.model.FlightBookingResponse;
import com.acme.air.exercise.model.FlightBookingStatus;
import com.acme.air.exercise.repo.SeatRepository;
import com.acme.air.exercise.service.FlightBookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {

  private final SeatRepository seatRepository;

  private final SeatDTOMapper seatDTOMapper;

  @Override
  @Transactional
  public FlightBookingResponse bookFlight(final FlightBookingRequest request) {
    FlightBookingResponse response = new FlightBookingResponse();
    Optional<Seat> seat = seatRepository.findTopByFlightInstanceIdAndSeatTypeAndIsBookedFalse(
        request.getFlightInstanceId(), request.getSeatType());

    if (seat.isPresent()) {
      Seat bookedSeat = seat.get();
      bookedSeat.setBooked(true);
      seatRepository.save(bookedSeat);
      response.setSeat(seatDTOMapper.map(bookedSeat));
      response.setStatus(FlightBookingStatus.SUCCESS);
    } else {
      response.setStatus(FlightBookingStatus.NO_AVAILABLE_SEATS);
    }

    return response;
  }
}
