package com.example.demo.model;

import com.example.demo.entity.CityEntity;

public class CityScored {
    private String cityName;
    private double latitude;
    private double longitude;
    private double score;

    public CityScored() {
    }

    public static CityScored toModel(CityEntity entity, double score) {
        CityScored model = new CityScored();
        model.setCityName(entity.getCityName());
        model.setLatitude(entity.getLatitude());
        model.setLongitude(entity.getLongitude());
        model.setScore(score);
        return model;
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
