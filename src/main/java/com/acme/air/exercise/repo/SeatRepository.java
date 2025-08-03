package com.acme.air.exercise.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.entity.SeatType;

import jakarta.persistence.LockModeType;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Seat> findTopByFlightInstanceIdAndSeatTypeAndIsBookedFalse(Long flightInstanceId, SeatType seatType);
}
