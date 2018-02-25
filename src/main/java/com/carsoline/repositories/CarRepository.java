package com.carsoline.repositories;

import com.carsoline.rest.daos.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dominik on 2017-04-13.
 */
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from car c where c.id = ?1")
    Car findCarById(@Param("id") Long id);

    Car findOneById(Long id);

    List<Car> findByBrand(String brand);

    Collection<Car> findByModel(String model);

    Collection<Car> findByModelAndBrand(String model, String brand);

    Collection<Car> findByProductionStartDateGreaterThanEqualAndProductionEndDateLessThanEqual(Integer productionStartDate, Integer productionEndDate);

    Long countAllByBrand(String brand);

}