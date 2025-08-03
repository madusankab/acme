package com.acme.air.exercise.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class FlightInstance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Flight flight;

  private LocalDate departureDate;

  private LocalTime departureTime;

  private LocalTime arrivalTime;

  @ToString.Exclude
  @OneToMany(mappedBy = "flightInstance", cascade = CascadeType.ALL)
  private List<Seat> seats = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private FlightInstanceStatus status;

  @CreatedDate
  private Instant createdDate;

  @LastModifiedDate
  private Instant modifiedDate;

}
