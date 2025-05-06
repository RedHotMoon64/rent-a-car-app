package org.example.rent.a.car.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.rent.a.car.app.enums.CarType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private int id;
    private String brand;
    private String model;
    private int price;
    private CarType type;
    private String color;
}
