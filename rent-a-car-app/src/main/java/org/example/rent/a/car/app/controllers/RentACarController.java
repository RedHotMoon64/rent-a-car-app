package org.example.rent.a.car.app.controllers;

import lombok.AllArgsConstructor;
import org.example.rent.a.car.app.dtos.CarDTO;
import org.example.rent.a.car.app.dtos.CarTypeDTO;
import org.example.rent.a.car.app.dtos.PriceDTO;
import org.example.rent.a.car.app.services.CarProcessingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

//todo refactor using CQRS architecture

@RestController
@AllArgsConstructor
@RequestMapping("rent-a-car")
public class RentACarController {
    private final CarProcessingService carProcessingService;

    @GetMapping("/get-all")
    public List<CarDTO> getAllCars() {
        return carProcessingService.getAllCars();
    }

    @GetMapping("/car-types")
    public List<CarTypeDTO> getAllCarTypes() {
        return carProcessingService.getAllCarTypes();
    }

    @PostMapping("/add")
    public void addNewCar(@RequestBody @Valid CarDTO carDTO) {
        carProcessingService.addNewCar(carDTO);
    }

    @PutMapping("/update-price/{id}")
    public void updateCarPrice(@PathVariable int id, @RequestBody PriceDTO priceDTO) {
        carProcessingService.updateCarPrice(id, priceDTO);
    }
}

