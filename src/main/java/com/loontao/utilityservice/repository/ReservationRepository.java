package com.loontao.utilityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loontao.utilityservice.entity.ReservationEntity;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByPhoneNumber(String phoneNumber);

}
