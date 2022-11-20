package com.example.demo.service;


import com.example.demo.entity.CityEntity;
import com.example.demo.exception.CityAlreadyExistException;
import com.example.demo.exception.CityNotFoundException;
import com.example.demo.model.CityScored;
import com.example.demo.repository.CityRepo;
import com.example.demo.repository.CityRepoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<CityScored> findAll(String q, String latitude, String longitude) {
        //String[] parsed = parse(q);
        List<CityEntity> list = cityRepoList.findAllByCityNameContaining(q);
        return toScored(list, latitude, longitude);
    }
//    public List<CityScored> findAll(String q) {
//        String[] parsed = parse(q);
//        List<CityEntity> list = cityRepoList.findAllByCityNameContaining(parsed[0]);
//        return toScored(list,parsed);
//    }

    public String[] parse (String q){
        String[] parsed = q.split("&");
        double latitude = 0;
        double longitude = 0;
        //parsed[0]= parsed[0].substring(2);
        if (parsed[1].startsWith("latitude="))
              latitude = Double.parseDouble(parsed[1].substring(9));
        if (parsed[2].startsWith("longitude="))
            longitude = Double.parseDouble(parsed[2].substring(10));
        parsed[1]= String.valueOf(latitude);
        parsed[2]= String.valueOf(longitude);
        return parsed;
    }

    public List<CityScored> toScored(List<CityEntity> list, String latitude, String longitude){
        List<CityScored> result = new ArrayList<>();
        for (CityEntity res:list) {
            result.add(CityScored.toModel(res,calculateScore(res, latitude,longitude)));
        }
        Collections.sort(result, CityScored.COMPARE_BY_SCORE);
        //result.sort(CityScored.COMPARE_BY_SCORE);
        return result;
    }

    public double calculateScore(CityEntity city, String latitude, String longitude){
        double score, longSum;
        //DecimalFormat df = new DecimalFormat("#.#");
        double cityLat = city.getLatitude();//широта
        double cityLong = city.getLongitude();//долгота
        double reqLatitude = Double.parseDouble(latitude);
        double reqLongitude = Double.parseDouble(longitude);
        longSum = Math.abs(reqLongitude - cityLong);
        if(longSum>180) longSum = 360 - longSum;
        score = (360-(Math.abs(reqLatitude - cityLat) + longSum))/360;
//        score = Double.parseDouble(df.format((
//                (Math.abs(reqLatitude - cityLat) + longSum)/360)));
        double scale = Math.pow(10, 1);
        double result = Math.ceil(score * scale) / scale;
        return result;
    }
}
