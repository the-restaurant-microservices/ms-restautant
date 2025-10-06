package com.example.msrestaurant.mapper;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import org.apache.commons.lang3.StringUtils;

import static com.example.msrestaurant.enums.Status.ACTIVE;
import static com.example.msrestaurant.enums.Status.IN_PROGRESS;

public enum RestaurantMapper {

    RESTAURANT_MAPPER;

    public RestaurantEntity requestToEntity(CreateRestaurantRequest request) {
        return RestaurantEntity.builder()
                .status(ACTIVE)
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
        entity.setStatus(IN_PROGRESS);
        if (StringUtils.isNotEmpty(request.getName())) {
            entity.setName(request.getName());
        }
        if (StringUtils.isNotEmpty(request.getAddress())) {
            entity.setAddress(request.getAddress());
        }
    }
}
