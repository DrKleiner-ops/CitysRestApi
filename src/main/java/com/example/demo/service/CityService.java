package com.example.demo.service;


import com.example.demo.entity.CityEntity;
import com.example.demo.exception.CityAlreadyExistException;
import com.example.demo.exception.CityNotFoundException;
import com.example.demo.repository.CityRepo;
import com.example.demo.repository.CityRepoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CityRepoList cityRepoList;


    public CityEntity addCity(CityEntity city) throws CityAlreadyExistException{
        if(cityRepo.findByCityName(city.getCityName())!=null){
            throw new CityAlreadyExistException("Город с таким названием уже существует");
        }
        return cityRepo.save(city);
    }

    public CityEntity getCity(String cityName) throws CityNotFoundException {

        CityEntity city = cityRepo.findByCityNameContaining(cityName);
        if(cityRepo.findByCityName(city.getCityName()) == null){
            throw new CityNotFoundException("Город не был найден");
        }
        return city;
    }

    public List<CityEntity> findAll(String cityName) {
        return cityRepoList.findAllByCityNameContaining(cityName);
    }

}
