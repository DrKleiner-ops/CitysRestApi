package com.example.demo.exception;

public class CityAlreadyExistException extends Exception{
    public CityAlreadyExistException(String message) {
        super(message);
    }
}
