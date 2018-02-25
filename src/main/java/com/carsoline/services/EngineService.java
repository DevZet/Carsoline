package com.carsoline.services;

import com.carsoline.repositories.EngineRepository;
import com.carsoline.rest.daos.Car;
import com.carsoline.rest.daos.Engine;
import com.carsoline.rest.dtos.EngineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Dominik on 2017-04-12.
 */
@Component
public class EngineService {

    private final EngineRepository engineRepository;

    @Autowired
    public EngineService(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    public List<Engine> getEngines() {
        return engineRepository.findAll();
    }

    public Engine save(Engine engine) {
        return engineRepository.save(engine);
    }

    public Engine findById(Long id) {
        return engineRepository.findOneById(id);
    }

    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    public List<Engine> findByCarId(Long carId) {
        return engineRepository.findByCarId(carId);
    }

    public void delete(Long id) {
        engineRepository.delete(id);
    }
}
