package com.loontao.utilityservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loontao.utilityservice.dto.RestaurantInfoDTO;
import com.loontao.utilityservice.entity.RestaurantInfoEntity;
import com.loontao.utilityservice.exceptions.ResourceNotFoundException;
import com.loontao.utilityservice.service.RestaurantInfoService;

import java.util.List;

@RestController
@RequestMapping("/restaurant-info")
public class RestaurantInfoController {

    private final RestaurantInfoService restaurantInfoService;

    public RestaurantInfoController(RestaurantInfoService timingService) {
        this.restaurantInfoService = timingService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<RestaurantInfoEntity>> getAllRestaurantInfos() {
       List<RestaurantInfoEntity> restaurantInfoTimes = restaurantInfoService.getAllRestaurantInfos();
       return ResponseEntity.ok(restaurantInfoTimes);
    }

    @GetMapping("/get/{id}")
    public RestaurantInfoEntity getRestaurantInfoById(@PathVariable Long id) {
        return restaurantInfoService.getRestaurantInfoById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found"));
    }

    @PostMapping("/add")
    public RestaurantInfoEntity addRestaurantInfo(@RequestBody RestaurantInfoDTO restaurantInfoDTO) {
        return restaurantInfoService.addRestaurantInfo(restaurantInfoDTO);
    }

    @PutMapping("/update/{id}")
    public RestaurantInfoEntity updateRestaurantInfoById(@PathVariable Long id, @RequestBody RestaurantInfoDTO restaurantInfoDTO) {
        return restaurantInfoService.updateRestaurantInfoById(id, restaurantInfoDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRestaurantInfoById(@PathVariable Long id) {
        restaurantInfoService.deleteRestaurantInfoById(id);
    }
}
