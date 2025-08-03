package com.acme.air.exercise.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.entity.SeatType;
import com.acme.air.exercise.mapper.SeatDTOMapper;
import com.acme.air.exercise.model.FlightBookingRequest;
import com.acme.air.exercise.model.FlightBookingResponse;
import com.acme.air.exercise.model.FlightBookingStatus;
import com.acme.air.exercise.model.SeatDTO;
import com.acme.air.exercise.repo.SeatRepository;
import com.acme.air.exercise.service.impl.FlightBookingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class FlightBookingServiceTest {

  @Mock
  private SeatRepository seatRepository;

  @Mock
  private SeatDTOMapper seatDTOMapper;

  @InjectMocks
  private FlightBookingServiceImpl flightBookingService;

  @Test
  void testBookFlight_whenSeatExists() {
    Long flightInstanceId = 1L;
    SeatType seatType = SeatType.ECONOMY_CLASS;
    Seat seat = new Seat();
    seat.setId(100L);
    seat.setSeatType(seatType);
    seat.setBooked(false);

    SeatDTO seatDTO = new SeatDTO();

    FlightBookingRequest request = new FlightBookingRequest();
    request.setFlightInstanceId(flightInstanceId);
    request.setSeatType(seatType);

    when(seatRepository.findTopByFlightInstanceIdAndSeatTypeAndIsBookedFalse(flightInstanceId,
        seatType))
        .thenReturn(Optional.of(seat));
    when(seatDTOMapper.map(seat)).thenReturn(seatDTO);
    when(seatRepository.save(any(Seat.class))).thenReturn(seat);

    FlightBookingResponse response = flightBookingService.bookFlight(request);

    assertEquals(FlightBookingStatus.SUCCESS, response.getStatus());
    assertEquals(seatDTO, response.getSeat());
    assertTrue(seat.isBooked());
    verify(seatRepository).findTopByFlightInstanceIdAndSeatTypeAndIsBookedFalse(flightInstanceId,
        seatType);
    verify(seatRepository).save(seat);
    verify(seatDTOMapper).map(seat);
  }

  @Test
  void testBookFlight_whenSeatDoesNotExist() {
    Long flightInstanceId = 1L;
    SeatType seatType = SeatType.BUSINESS_CLASS;

    FlightBookingRequest request = new FlightBookingRequest();
    request.setFlightInstanceId(flightInstanceId);
    request.setSeatType(seatType);

    when(seatRepository.findTopByFlightInstanceIdAndSeatTypeAndIsBookedFalse(flightInstanceId,
        seatType)).thenReturn(Optional.empty());

    FlightBookingResponse response = flightBookingService.bookFlight(request);

    assertEquals(FlightBookingStatus.NO_AVAILABLE_SEATS, response.getStatus());
    assertNull(response.getSeat());
    verify(seatRepository, never()).save(any());
    verify(seatDTOMapper, never()).map(any(Seat.class));
  }

}
