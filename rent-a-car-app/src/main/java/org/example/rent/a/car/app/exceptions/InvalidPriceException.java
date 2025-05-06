package org.example.rent.a.car.app.exceptions;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException(String msg) {
        super(msg);
    }
}
