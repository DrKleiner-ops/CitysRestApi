package com.example.demo.service;


import com.example.demo.entity.CityEntity;
import com.example.demo.exception.CityAlreadyExistException;
import com.example.demo.exception.CityNotFoundException;
import com.example.demo.model.CityScored;
import com.example.demo.repository.CityRepo;
import com.example.demo.repository.CityRepoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;
    @Autowired
    private CityRepoList cityRepoList;


    public CityEntity addCity(CityEntity city) throws CityAlreadyExistException {
        if (cityRepo.findByCityName(city.getCityName()) != null) {
            throw new CityAlreadyExistException("Город с таким названием уже существует");
        }
        return cityRepo.save(city);
    }

    @Deprecated
    public CityEntity getCity(String cityName) throws CityNotFoundException {

        CityEntity city = cityRepo.findByCityNameContaining(cityName);
        if (cityRepo.findByCityName(city.getCityName()) == null) {
            throw new CityNotFoundException("Город не был найден");
        }
        return city;
    }

    public List<CityScored> findAll(String q, String latitude, String longitude) {
        List<CityEntity> list = cityRepoList.findAllByCityNameContaining(q);
        return toScored(list, latitude, longitude);
    }

    public List<CityScored> toScored(List<CityEntity> list, String latitude, String longitude) {
        List<CityScored> result = new ArrayList<>();
        for (CityEntity res : list) {
            result.add(CityScored.toModel(res, calculateScore(res, latitude, longitude)));
        }
        result.sort((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        return result;
    }

    public double calculateScore(CityEntity city, String latitude, String longitude) {
        double score, longSum;
        double cityLat = city.getLatitude();//широта
        double cityLong = city.getLongitude();//долгота
        double reqLatitude = Double.parseDouble(latitude);
        double reqLongitude = Double.parseDouble(longitude);
        longSum = Math.abs(reqLongitude - cityLong);
        if (longSum > 180) longSum = 360 - longSum;
        score = (360 - (Math.abs(reqLatitude - cityLat) + longSum)) / 360;
        double scale = Math.pow(10, 1);
        return Math.ceil(score * scale) / scale;
    }
}
