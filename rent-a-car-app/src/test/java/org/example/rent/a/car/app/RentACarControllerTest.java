package org.example.rent.a.car.app;

import org.example.rent.a.car.app.controllers.RentACarController;
import org.example.rent.a.car.app.dtos.CarDTO;
import org.example.rent.a.car.app.dtos.PriceDTO;
import org.example.rent.a.car.app.entities.Car;
import org.example.rent.a.car.app.enums.CarType;
import org.example.rent.a.car.app.exceptions.CarNotFoundException;
import org.example.rent.a.car.app.repositories.CarRepository;
import org.example.rent.a.car.app.services.PriceValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentACarControllerTest {
    private CarRepository carRepository;
    private PriceValidationService priceValidationService;
    private RentACarController controller;
    List<Car> carList = new ArrayList<>();
    List<CarDTO> result = new ArrayList<>();
    CarDTO carDTO1 = new CarDTO(1, "brand", "model", 30, CarType.STANDARD, "red");
    Car car3 = new Car();
    PriceDTO priceDTO = new PriceDTO();

    @BeforeEach
    void beforeEach() {
        carRepository = mock(CarRepository.class);
        priceValidationService = mock(PriceValidationService.class);
        controller = new RentACarController(carRepository, priceValidationService);
        Car car1 = new Car(1, "brand", "model", 30, CarType.STANDARD, "red");
        Car car2 = new Car(2, "brand2", "model2", 31, CarType.STANDARD, "red2");
        carList.add(car1);
        carList.add(car2);
    }

    @Test
    void should_return_all_cars() {
        when(carRepository.findAll()).thenReturn(carList);
        result = controller.getAllCars();
        assertEquals(result.size(),carList.size());
    }

    @Test
    void should_have_the_same_values() {
        when(carRepository.findAll()).thenReturn(carList);
        result = controller.getAllCars();
        assertEquals(result.get(0).getId(), carList.get(0).getId());
        assertEquals(result.get(1).getId(), carList.get(1).getId());
    }

    @Test
    void should_call_validate_price_at_least_once_when_add_new_car() {
        controller.addNewCar(carDTO1);
        verify(priceValidationService, times(1)).validatePrice(carDTO1.getType(), carDTO1.getPrice());
    }

    @Test
    void should_call_validate_price_at_least_once_when_update_car() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car3));
        controller.updateCarPrice(1, priceDTO);
        verify(priceValidationService, times(1)).validatePrice(Optional.of(car3).get().getType(), priceDTO.getPrice());
    }

    @Test
    void should_throw_car_not_found_exception_when_car_is_not_found() {
        assertThrows(CarNotFoundException.class, () -> controller.updateCarPrice(1, priceDTO));
    }

    @Test
    void should_call_find_all_type_once() {
        when(carRepository.findAllAndCountByType()).thenReturn(new ArrayList<>());
        controller.getAllCarTypes();
        verify(carRepository, times(1)).findAllAndCountByType();
    }
}
