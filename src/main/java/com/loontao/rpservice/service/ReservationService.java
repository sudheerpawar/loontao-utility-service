package com.loontao.rpservice.service;

import com.loontao.rpservice.entity.Reservation;
import com.loontao.rpservice.exceptions.ResourceNotFoundException;
import com.loontao.rpservice.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation addReservation(Reservation reservation) {
        return repository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return repository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return repository.findById(id);
    }

    public List<Reservation> getReservationByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }


    public Reservation updateReservation(Long id, Reservation reservation) {
        if (repository.existsById(id)) {
            reservation.setId(id);
            return repository.save(reservation);
        }
        throw new ResourceNotFoundException("Reservation with ID " + id + " not found.");
    }

    public void deleteReservation(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation with ID " + id + " not found.");
        }
        repository.deleteById(id);
    }

}
