package com.acme.air.exercise.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String seatNumber;

  @Enumerated(EnumType.STRING)
  private SeatType seatType;

  private boolean isBooked;

  @ToString.Exclude
  @ManyToOne
  private FlightInstance flightInstance;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant modifiedDate;

}
