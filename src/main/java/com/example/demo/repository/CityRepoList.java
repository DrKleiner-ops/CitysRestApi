package com.example.demo.repository;

import com.example.demo.entity.CityEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepoList extends CrudRepository<CityEntity, String> {
    List<CityEntity> findAllByCityNameContaining(String cityName);
}