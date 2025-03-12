package com.loontao.utilityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loontao.utilityservice.entity.RestaurantInfoEntity;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfoEntity, Long> {
}
