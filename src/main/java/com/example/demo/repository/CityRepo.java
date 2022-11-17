package com.example.demo.repository;

import com.example.demo.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

public interface CityRepo extends CrudRepository <CityEntity, String>{
    CityEntity findByCityName(String cityName);
    CityEntity findByCityNameContaining(String cityName);
    CityEntity findAllByCityNameContaining(String cityName);
}
