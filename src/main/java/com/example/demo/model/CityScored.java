package com.example.demo.model;

import com.example.demo.entity.CityEntity;

import java.util.Comparator;

public class CityScored {
    private String cityName;
    private double latitude;
    private double longitude;
    private double score;

    public static CityScored toModel(CityEntity entity, double score){
        CityScored model = new CityScored();
        model.setCityName(entity.getCityName());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setScore(score);
        return model;
    }

    public static final Comparator<CityScored> COMPARE_BY_SCORE = new Comparator<CityScored>() {
        @Override
        public int compare(CityScored lhs, CityScored rhs) {
            return (int) (rhs.getScore() - lhs.getScore());
        }
    };

    public CityScored() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }





}
