package com.acme.air.exercise.entity;

import java.time.Instant;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Flight {

  @Id
  private String flightId;

  private String origin;

  private String destination;

  private LocalTime departureTime;

  private LocalTime arrivalTime;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant modifiedDate;

}
