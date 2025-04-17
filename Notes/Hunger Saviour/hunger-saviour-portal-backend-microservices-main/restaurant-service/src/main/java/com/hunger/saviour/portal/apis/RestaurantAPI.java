package com.hunger.saviour.portal.apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunger.saviour.portal.dtos.RestaurantDTO;
import com.hunger.saviour.portal.entities.RestaurantEntity;
import com.hunger.saviour.portal.repositories.RestaurantRepository;
import com.hunger.saviour.portal.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantAPI {

    private final RestaurantService restaurantService;

    private final ObjectMapper objectMapper;

    private RestaurantRepository restaurantRepository;
    private ResourceLoader resourceLoader;

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO){
        RestaurantDTO restaurant = this.restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<RestaurantDTO>(restaurant,HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public RestaurantDTO getRestaurantById(@PathVariable Integer restaurantId){
        return this.restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping
    public String test() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + "rest.json");
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String j = FileCopyUtils.copyToString(reader);
            List<RestaurantEntity> restaurantEntities = objectMapper.readValue(j
                    , new TypeReference<List<RestaurantEntity>>() {}
            );
            this.restaurantRepository.saveAll(restaurantEntities);
        }
        return "Records inserted";
    }

    @GetMapping("/test/hi")
    public String another(){
        return "Hi accessed";
    }

    @GetMapping("/{offset}/{pagesize}")
    public ResponseEntity<Page<RestaurantDTO>> getRestaurants(@PathVariable int offset, @PathVariable int pagesize){
        return ResponseEntity.ok(restaurantService.getRestaurants(offset, pagesize));
    }

}
