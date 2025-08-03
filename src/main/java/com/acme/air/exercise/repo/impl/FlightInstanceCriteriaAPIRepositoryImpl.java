package com.acme.air.exercise.repo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.acme.air.exercise.entity.Flight;
import com.acme.air.exercise.entity.FlightInstance;
import com.acme.air.exercise.entity.Seat;
import com.acme.air.exercise.model.FlightSearchRequest;
import com.acme.air.exercise.repo.FlightInstanceCriteriaAPIRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FlightInstanceCriteriaAPIRepositoryImpl implements FlightInstanceCriteriaAPIRepository {

  private final EntityManager entityManager;

  public List<FlightInstance> searchFlightInstances(final FlightSearchRequest request) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<FlightInstance> cq = cb.createQuery(FlightInstance.class);
    Root<FlightInstance> fi = cq.from(FlightInstance.class);

    Join<FlightInstance, Flight> flight = fi.join("flight");
    Join<FlightInstance, Seat> seat = fi.join("seats", JoinType.LEFT);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(flight.get("origin"), request.getOrigin()));
    predicates.add(cb.equal(flight.get("destination"), request.getDestination()));
    predicates.add(cb.equal(fi.get("departureDate"), request.getDepartureDate()));
    predicates.add(cb.isFalse(seat.get("isBooked")));

    cq.select(fi)
        .where(cb.and(predicates.toArray(new Predicate[0])))
        .groupBy(fi.get("id"))
        .having(cb.ge(cb.count(seat.get("id")), request.getNumOfSeats()));

    return entityManager.createQuery(cq).getResultList();
  }

}
