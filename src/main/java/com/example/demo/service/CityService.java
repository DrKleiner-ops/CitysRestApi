package com.example.demo.service;


import com.example.demo.entity.CityEntity;
import com.example.demo.exception.CityAlreadyExistException;
import com.example.demo.exception.CityNotFoundException;
import com.example.demo.model.CityScored;
import com.example.demo.model.Coordinates;
import com.example.demo.repository.CityRepo;
import com.example.demo.repository.CityRepoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

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

    public List<CityScored> findAll(String q) {
        String parsed = parse(q);
        List<CityEntity> list = cityRepoList.findAllByCityNameContaining(parsed);
        return toScored(list);
    }

    public String parse (String q){
        String[] parsed = q.split("&");
        double latitude = 0;
        double longitude = 0;
        if (parsed[1].startsWith("latitude="))
              latitude = Double.parseDouble(parsed[1].substring(8));
        if (parsed[2].startsWith("longitude="))
            longitude = Double.parseDouble(parsed[2].substring(9));
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        return parsed[0];
    }

    public List<CityScored> toScored(List<CityEntity> list){
        List<CityScored> result = new ArrayList<>();
        for (CityEntity res:list) {
            result.add(CityScored.toModel(res,calculateScore(res)));
        }
        result.sort(CityScored.COMPARE_BY_SCORE);
        return result;
    }

    public double calculateScore(CityEntity city){
        double score, longSum;
        DecimalFormat df = new DecimalFormat("#.#");
        double cityLat = city.getLatitude();//широта
        double cityLong = city.getLongitude();//долгота
        longSum = Math.abs(longitude - cityLong);
        if(longSum>180) longSum = 360 - longSum;
        score = Double.parseDouble(df.format((
                (Math.abs(latitude - cityLat) + longSum)/360)));

        return score;
    }
}
