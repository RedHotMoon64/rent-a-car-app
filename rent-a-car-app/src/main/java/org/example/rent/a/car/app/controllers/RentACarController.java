package org.example.rent.a.car.app.controllers;

import lombok.AllArgsConstructor;
import org.example.rent.a.car.app.dtos.CarDTO;
import org.example.rent.a.car.app.dtos.CarTypeDTO;
import org.example.rent.a.car.app.dtos.PriceDTO;
import org.example.rent.a.car.app.entities.Car;
import org.example.rent.a.car.app.enums.CarType;
import org.example.rent.a.car.app.exceptions.CarNotFoundException;
import org.example.rent.a.car.app.repositories.CarRepository;
import org.example.rent.a.car.app.services.PriceValidationService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//todo refactor using CQRS architecture

@RestController
@AllArgsConstructor
@RequestMapping("rent-a-car")
public class RentACarController {
    private final CarRepository carRepository;
    private final PriceValidationService priceValidationService;

    @GetMapping("/get-all")
    public List<CarDTO> getAllCars() {
        //todo move in a separate service
        return carRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Car::getPrice))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/car-types")
    public List<CarTypeDTO> getAllCarTypes() {
        //todo move in a separate service
        return carRepository.findAllAndCountByType()
                .stream()
                .map(carTypeModel -> new CarTypeDTO(CarType.getEnumByString(carTypeModel.getType()) ,carTypeModel.getCount()))
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public void addNewCar(@RequestBody @Valid CarDTO carDTO) {
        //todo move in a separate service
        priceValidationService.validatePrice(carDTO.getType(), carDTO.getPrice());
        carRepository.saveAndFlush(createCarEntity(carDTO));
    }

    @PutMapping("/update-price/{id}")
    public void updateCarPrice(@PathVariable int id, @RequestBody PriceDTO priceDTO) {
        //todo move in a separate service
        Car car = this.carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("There is no car with id: " + id));
        priceValidationService.validatePrice(car.getType(), priceDTO.getPrice());
        car.setPrice(priceDTO.getPrice());
        this.carRepository.saveAndFlush(car);
    }

    private CarDTO mapToDTO(Car car) {
        return new CarDTO(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPrice(),
                car.getType(),
                car.getColor()
        );
    }
    private Car createCarEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setPrice(carDTO.getPrice());
        car.setType(carDTO.getType());
        car.setModel(carDTO.getModel());
        car.setBrand(carDTO.getBrand());
        car.setColor(carDTO.getColor());
        return car;
    }
}

