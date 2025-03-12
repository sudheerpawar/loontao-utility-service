package com.loontao.utilityservice.service;

import org.springframework.stereotype.Service;

import com.loontao.utilityservice.dto.RestaurantInfoDTO;
import com.loontao.utilityservice.entity.RestaurantInfoEntity;
import com.loontao.utilityservice.repository.RestaurantInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantInfoService {

    private RestaurantInfoRepository restaurantInfoRepository;

    public RestaurantInfoService(RestaurantInfoRepository restaurantInfoRepository) {
        this.restaurantInfoRepository = restaurantInfoRepository;
    }

    public List<RestaurantInfoEntity> getAllRestaurantInfos() {
        List<RestaurantInfoEntity> restaurantInfoEntity = new ArrayList<>();
        restaurantInfoRepository.findAll().forEach(restaurantInfoEntity::add);
        return restaurantInfoEntity;
    }

    public Optional<RestaurantInfoEntity> getRestaurantInfoById(Long id) {
        return restaurantInfoRepository.findById(id);
    }

    public RestaurantInfoEntity addRestaurantInfo(RestaurantInfoDTO restaurantInfoDTO) {
        RestaurantInfoEntity restaurantInfoEntity = new RestaurantInfoEntity();
        restaurantInfoEntity.setDay(restaurantInfoDTO.getDay());
        restaurantInfoEntity.setStartTime(restaurantInfoDTO.getStartTime());
        restaurantInfoEntity.setEndTime(restaurantInfoDTO.getEndTime());
        restaurantInfoEntity.setContact(restaurantInfoDTO.getContact());
        return restaurantInfoRepository.save(restaurantInfoEntity);
    }

    public RestaurantInfoEntity updateRestaurantInfoById(Long id, RestaurantInfoDTO restaurantInfoDTO) {
        return restaurantInfoRepository.findById(id).map(restaurantInfoEntity -> {
            restaurantInfoEntity.setDay(restaurantInfoDTO.getDay());
            restaurantInfoEntity.setStartTime(restaurantInfoDTO.getStartTime());
            restaurantInfoEntity.setEndTime(restaurantInfoDTO.getEndTime());
            restaurantInfoEntity.setContact(restaurantInfoDTO.getContact());
            return restaurantInfoRepository.save(restaurantInfoEntity);
        }).orElseThrow(() -> new RuntimeException("Timing not found"));
    }

    public void deleteRestaurantInfoById(Long id) {
        restaurantInfoRepository.deleteById(id);
    }


}
