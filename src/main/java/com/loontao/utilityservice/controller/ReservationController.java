package com.loontao.utilityservice.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loontao.utilityservice.dto.JwtDTO;
import com.loontao.utilityservice.dto.ReservationDTO;
import com.loontao.utilityservice.entity.ReservationEntity;
import com.loontao.utilityservice.exceptions.ResourceNotFoundException;
import com.loontao.utilityservice.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<ReservationEntity> addReservation(@RequestBody ReservationDTO reservationDTO) {
        ReservationEntity reservation = service.addReservation(reservationDTO);
        return ResponseEntity.ok().body(reservation);
    }

    @GetMapping("/getAll")
    public List<ReservationEntity> getReservations() {
        return service.getAllReservations();
    }

    @GetMapping("/get/{id}")
    public ReservationEntity getReservation(@PathVariable Long id) {
        return service.getReservationById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
    }

    @GetMapping("/get/{phoneNumber}")
    public List<ReservationEntity> getReservationByPhoneNumber(@PathVariable String phoneNumber) {
        return service.getReservationByPhoneNumber(phoneNumber);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationEntity> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO reservationDTO) {
        ReservationEntity reservation = service.updateReservation(id, reservationDTO);
        return ResponseEntity.ok().body(reservation);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReservation(@PathVariable Long id) {
        service.deleteReservation(id);
    }

    @GetMapping("/generate-token/{email}")
    public ResponseEntity<JwtDTO> generateTokenByEmail(@PathVariable String email) {
        try {
            return service.generateTokenByEmail(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtDTO("An error occurred during token generation", null));
        }
    }   
}
