package com.example.msrestaurant.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateRestaurantRequest {

    @NotBlank(message = "Name cannot be blank")
    String name;

    @NotBlank(message = "Address cannot be blank")
    String address;
}
