package com.carsoline.rest.dataAssemblers;

import com.carsoline.rest.daos.Car;
import com.carsoline.rest.dtos.CarDto;
import com.carsoline.rest.dtos.LinkDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 2017-04-26.
 */
public class CarDataAssembler {

    public static CarDto toCarDto(Car car){
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand() == null ? "" : car.getBrand());
        carDto.setModel(car.getModel() == null ? "" : car.getModel());
        carDto.setLinks(new ArrayList<LinkDto>() {{
            add(new LinkDto("self", "/cars/" + car.getId(), "get, put, delete"));
            add(new LinkDto("engines", "/cars/" + car.getId() + "/engines", "get"));
        }});
        carDto.setProductionStartDate(car.getProductionStartDate());
        carDto.setProductionEndDate(car.getProductionEndDate());

        return carDto;
    }


    public static List<CarDto> toCarDtoCollection(Collection<Car> collection){
        return  collection == null ? Collections.emptyList() : collection.stream()
                .map(CarDataAssembler::toCarDto)
                .collect(Collectors.toList());
    }

}
