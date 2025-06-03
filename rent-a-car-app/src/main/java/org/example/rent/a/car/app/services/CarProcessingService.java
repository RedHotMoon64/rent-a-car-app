package org.example.rent.a.car.app.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.rent.a.car.app.dtos.CarDTO;
import org.example.rent.a.car.app.dtos.CarTypeDTO;
import org.example.rent.a.car.app.dtos.PriceDTO;
import org.example.rent.a.car.app.entities.Car;
import org.example.rent.a.car.app.enums.CarType;
import org.example.rent.a.car.app.exceptions.CarNotFoundException;
import org.example.rent.a.car.app.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarProcessingService {

    private final CarRepository carRepository;
    private final PriceValidationService priceValidationService;

    public List<CarDTO> getAllCars() {
        return carRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Car::getPrice))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CarTypeDTO> getAllCarTypes() {
        return carRepository.findAllAndCountByType()
                .stream()
                .map(carTypeModel -> new CarTypeDTO(CarType.getEnumByString(carTypeModel.getType()) ,carTypeModel.getCount()))
                .collect(Collectors.toList());
    }

    public void addNewCar(CarDTO carDTO) {
        priceValidationService.validatePrice(carDTO.getType(), carDTO.getPrice());
        carRepository.saveAndFlush(createCarEntity(carDTO));
    }

    public void updateCarPrice(int id, PriceDTO priceDTO) {
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
