package com.carsoline.repositories;

import com.carsoline.rest.daos.Car;
import com.carsoline.rest.daos.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Dominik on 2017-04-13.
 */
public interface EngineRepository extends JpaRepository<Engine, Long> {

    @Query("select e from engine e where e.id = ?1")
    Engine findEngineById(Long id);

    List<Engine> findByCarId(Long carId);

    Engine findOneById(Long id);

}
