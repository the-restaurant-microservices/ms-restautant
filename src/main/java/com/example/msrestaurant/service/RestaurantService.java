package com.example.msrestaurant.service;

import com.example.msrestaurant.dao.entity.RestaurantEntity;
import com.example.msrestaurant.dao.repository.RestaurantRepository;
import com.example.msrestaurant.dto.request.CreateRestaurantRequest;
import com.example.msrestaurant.dto.request.UpdateRestaurantRequest;
import com.example.msrestaurant.dto.response.RestaurantResponse;
import com.example.msrestaurant.mapper.RestaurantMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.msrestaurant.enums.Status.DELETED;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestaurantService {

    RestaurantRepository restaurantRepository;
    KafkaTemplate<String, String> kafkaTemplate;

    static String TOPIC = "restaurant.created";

    @CachePut(value = "restaurants", key = "#result.id")
    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {
        RestaurantEntity entity = RestaurantMapper.RESTAURANT_MAPPER.requestToEntity(request);

        RestaurantEntity saved = restaurantRepository.save(entity);

        String event = String.format("{\"id\":%d,\"name\":\"%s\",\"address\":\"%s\"}",
                saved.getId(), saved.getName(), saved.getAddress());
        kafkaTemplate.send(TOPIC, String.valueOf(saved.getId()), event);

        return RestaurantMapper.RESTAURANT_MAPPER.entityToResponse(saved);
    }

    @CachePut(value = "restaurants", key = "#id")
    public RestaurantResponse updateRestaurant(Long id, UpdateRestaurantRequest request) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        RestaurantMapper.RESTAURANT_MAPPER.updateRestaurant(entity, request);
        restaurantRepository.save(entity);
        return RestaurantMapper.RESTAURANT_MAPPER.entityToResponse(entity);
    }

    public List<RestaurantResponse> findAll() {
        return restaurantRepository.findAll().stream()
                .map(r -> new RestaurantResponse(r.getId(), r.getStatus(), r.getName(), r.getAddress()))
                .collect(Collectors.toList());
    }


    @Cacheable(value = "restaurants", key = "#id")
    public RestaurantResponse findById(Long id) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return RestaurantMapper.RESTAURANT_MAPPER.entityToResponse(entity);
    }

    @CacheEvict(value = "restaurants", key = "#id")
    public void deleteRestaurant(Long id) {
        RestaurantEntity entity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        entity.setStatus(DELETED);
        restaurantRepository.save(entity);
    }
}
