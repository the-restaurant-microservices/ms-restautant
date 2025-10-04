package com.example.msrestaurant.mapper;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import com.example.msrestaurant.enums.Status;

public enum RestaurantMapper {

    RESTAURANT_MAPPER;

    public RestaurantEntity requestToEntity(CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
                .status(Status.ACTIVE)
                .name(request.getName())
                .address(request.getAddress())
                .build();
    }

    public RestaurantResponse entityToResponse(RestaurantEntity entity) {
        return RestaurantResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .name(entity.getName())
                .address(entity.getAddress())
                .build();
    }

    public void updateRestaurant(RestaurantEntity entity, UpdateRestaurantRequest request) {
        entity.setStatus(Status.IN_PROGRESS);
        if (request.getName() != null && !request.getName().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            entity.setAddress(request.getAddress());
        }
    }
}
