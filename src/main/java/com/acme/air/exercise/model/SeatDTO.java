package com.acme.air.exercise.model;

import com.acme.air.exercise.entity.SeatType;

import lombok.Data;

@Data
public class SeatDTO {

  private String seatNumber;

  private SeatType seatType;

  private boolean isBooked;

}
