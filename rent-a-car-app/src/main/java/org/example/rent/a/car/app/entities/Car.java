package org.example.rent.a.car.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.rent.a.car.app.enums.CarType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Data
@AllArgsConstructor
public class Car {
    @Id
    @Column(name = "id", unique = true)
    private int id;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private int price;
    @NotNull
    private CarType type;
    @NotNull
    private String color;

    public Car() {
        this.id = (int)(Math.random()*100);
    }
}

