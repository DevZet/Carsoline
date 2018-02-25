package com.carsoline.services;

import com.carsoline.repositories.CarRepository;
import com.carsoline.rest.daos.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dominik on 2017-04-12.
 */
@Component
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car findOneById(Long id) {
        return carRepository.findOneById(id);
    }

    public Collection<Car> findByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    public Collection<Car> findByModel(String model) {
        return carRepository.findByModel(model);
    }

    public Collection<Car> findByBrandAndModel(String brand, String model) {
        return carRepository.findByModelAndBrand(model, brand);
    }

    public Collection<Car> findByProductionStartDateGreaterThanEqualAndProductionEndDateLessThanEqual(Integer productionStartDate, Integer productionEndDate) {
        return carRepository.findByProductionStartDateGreaterThanEqualAndProductionEndDateLessThanEqual(productionStartDate, productionEndDate);
    }

    public Long countByBrand(String brand) {
        return carRepository.countAllByBrand(brand);
    }

    public void delete(Long id) {
        carRepository.delete(id);
    }
}
