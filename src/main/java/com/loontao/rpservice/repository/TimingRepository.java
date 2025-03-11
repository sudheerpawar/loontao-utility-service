package com.loontao.rpservice.repository;

import com.loontao.rpservice.entity.Timing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimingRepository extends JpaRepository<Timing, Long> {
}
