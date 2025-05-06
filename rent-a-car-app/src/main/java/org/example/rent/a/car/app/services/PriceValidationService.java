package org.example.rent.a.car.app.services;

import org.example.rent.a.car.app.enums.CarType;
import org.example.rent.a.car.app.exceptions.InvalidPriceException;
import org.springframework.stereotype.Service;

//todo refactor this in a microservice

@Service
public class PriceValidationService {
    public void validatePrice(CarType carType, int price) {
        if (carType.equals(CarType.SUV)) {
            if (price < carType.getPriceRange()) {
                throw new InvalidPriceException("Invalid price for this car type!");
            }
        }
        else {
            if (price > carType.getPriceRange()) {
                throw new InvalidPriceException("Invalid price for this car type!");
            }
        }
    }
}
