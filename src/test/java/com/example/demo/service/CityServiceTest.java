package com.example.demo.service;

import com.example.demo.entity.CityEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {

    @Test
    void parse() {
        var test = new CityService();
        String s ="q=Londo&latitude=43.70011&longitude=-79.4163";
        String[] strings = {"Londo","43.70011","-79.4163"};
        Assertions.assertArrayEquals(strings,test.parse(s));

    }

    @Test
    void toScored() {
    }

//    @Test
//    void calculateScore() {
//        var test = new CityService();
//        CityEntity city = new CityEntity();
//        city.setCityName("wqer");
//        city.setId(1L);
//        city.setLatitude(143.23);
//        city.setLongitude(10.32);
//        String[] strings = {"wqer","10.321","10.123"};
//        Assertions.assertEquals(1,test.calculateScore(city,strings));
//
//    }
}