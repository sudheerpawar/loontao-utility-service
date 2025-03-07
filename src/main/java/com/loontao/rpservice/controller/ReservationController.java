package com.loontao.rpservice.controller;

import com.loontao.rpservice.entity.Reservation;
import com.loontao.rpservice.exceptions.ResourceNotFoundException;
import com.loontao.rpservice.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return service.addReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getReservations() {
        return service.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return service.getReservationById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    @GetMapping("/phone/{phoneNumber}")
    public List<Reservation> getReservationByPhoneNumber(@PathVariable String phoneNumber) {
        return service.getReservationByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        return service.updateReservation(id, reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
    }

}
