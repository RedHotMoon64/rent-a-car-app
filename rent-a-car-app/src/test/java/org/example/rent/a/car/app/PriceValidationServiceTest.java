package org.example.rent.a.car.app;

import org.example.rent.a.car.app.enums.CarType;
import org.example.rent.a.car.app.exceptions.InvalidPriceException;
import org.example.rent.a.car.app.services.PriceValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriceValidationServiceTest {

    private PriceValidationService priceValidationService;

    @BeforeEach
    void beforeEach() {
        priceValidationService = new PriceValidationService();
    }

    @Test
    void should_throw_invalid_price_exception_because_wrong_price_for_suv() {
        assertThrows(InvalidPriceException.class, () -> priceValidationService.validatePrice(CarType.SUV, 20));
    }

    @Test
    void should_throw_invalid_price_exception_because_wrong_price_for_economy() {
        assertThrows(InvalidPriceException.class, () -> priceValidationService.validatePrice(CarType.ECONOMY, 20));

    }
}
