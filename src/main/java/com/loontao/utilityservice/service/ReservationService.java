package com.loontao.utilityservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.loontao.utilityservice.dto.JwtDTO;
import com.loontao.utilityservice.dto.ReservationDTO;
import com.loontao.utilityservice.entity.ReservationEntity;
import com.loontao.utilityservice.exceptions.ResourceNotFoundException;
import com.loontao.utilityservice.repository.ReservationRepository;
import com.loontao.utilityservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, JwtService jwtService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ReservationEntity addReservation(ReservationDTO reservationDTO) {

        if (reservationDTO.getPhoneNumber() == null) {
            throw new ResourceNotFoundException("Phone number is required");
        }  
        if (reservationDTO.getDate() == null) {
            throw new ResourceNotFoundException("Date is required");
        }
        if (reservationDTO.getTime() == null) {
            throw new ResourceNotFoundException("Time is required");
        }
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setName(reservationDTO.getName());
        reservationEntity.setEmail(reservationDTO.getEmail());
        reservationEntity.setPhoneNumber(reservationDTO.getPhoneNumber());
        reservationEntity.setDate(reservationDTO.getDate());
        reservationEntity.setTime(reservationDTO.getTime());
        reservationEntity.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservationEntity.setComments(reservationDTO.getComments());
        return reservationRepository.save(reservationEntity);
    }

    public List<ReservationEntity> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<ReservationEntity> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<ReservationEntity> getReservationByPhoneNumber(String phoneNumber) {
        return reservationRepository.findByPhoneNumber(phoneNumber);
    }


    public ReservationEntity updateReservation(Long id, ReservationDTO reservationDTO) {
        if (reservationRepository.existsById(id)) {
            ReservationEntity reservationEntity = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation with ID " + id + " not found."));
            reservationEntity.setId(id);
            reservationEntity.setName(reservationDTO.getName());
            reservationEntity.setEmail(reservationDTO.getEmail());
            reservationEntity.setPhoneNumber(reservationDTO.getPhoneNumber());
            reservationEntity.setDate(reservationDTO.getDate());
            reservationEntity.setTime(reservationDTO.getTime());
            reservationEntity.setNumberOfGuests(reservationDTO.getNumberOfGuests());
            reservationEntity.setComments(reservationDTO.getComments());
            return reservationRepository.save(reservationEntity);
        }
        throw new ResourceNotFoundException("Reservation with ID " + id + " not found.");
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation with ID " + id + " not found.");
        }
        reservationRepository.deleteById(id);
    }

    public ResponseEntity<JwtDTO> generateTokenByEmail(String email) {
        if (userRepository.findByEmailId(email).isEmpty()) {
            throw new ResourceNotFoundException("User with email " + email + " not registered.");
        }
        String jwtToken = jwtService.generateToken(userRepository.findByEmailId(email).get());
        return ResponseEntity.ok(new JwtDTO(jwtToken, jwtService.extractExpiration(jwtToken).toString()));
    }   

}
