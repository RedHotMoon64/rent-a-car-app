package org.example.rent.a.car.app.repositories;

import org.example.rent.a.car.app.dtos.CarTypeDTO;
import org.example.rent.a.car.app.entities.Car;

import org.example.rent.a.car.app.models.CarTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    @Query(value = "SELECT type, count(*) from car Group By type", nativeQuery = true)
    List<CarTypeModel> findAllAndCountByType();

    Optional<Car> findById(int id);

}