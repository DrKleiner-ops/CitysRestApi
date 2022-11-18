package com.example.demo.controller;

import com.example.demo.entity.CityEntity;
import com.example.demo.exception.CityAlreadyExistException;
import com.example.demo.exception.CityNotFoundException;
import com.example.demo.repository.CityRepo;
import com.example.demo.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suggestions")
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping
    private ResponseEntity addCity(@RequestBody CityEntity city){
        try{
            cityService.addCity(city);
            return ResponseEntity.ok("Город успешно сохранен");
        }catch (CityAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
    @GetMapping
    public ResponseEntity getCitys(@RequestParam String q) {
        try {
            return ResponseEntity.ok(cityService.findAll(q));
        }// catch (CityNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
