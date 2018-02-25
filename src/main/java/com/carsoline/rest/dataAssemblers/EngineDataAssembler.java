package com.carsoline.rest.dataAssemblers;

import com.carsoline.rest.daos.Engine;
import com.carsoline.rest.dtos.EngineDto;
import com.carsoline.rest.dtos.LinkDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 2017-04-26.
 */
public class EngineDataAssembler {

    public static EngineDto toEngineDto(Engine e){
        return EngineDto.builder()
                .id(e.getId())
                .carId(e.getCar() != null ? e.getCar().getId() : null)
                .engineSymbol(e.getEngineSymbol() == null ? "" : e.getEngineSymbol())
                .enginePower(e.getEnginePower() == null ? "" : e.getEnginePower())
                .engineSize(e.getEngineSize() == null ? "" : e.getEngineSize())
                .fuelType(e.getFuelType() == null ? "" : e.getFuelType())
                .links(new ArrayList<LinkDto>() {{
                    add(new LinkDto("self", "/engines/"+e.getId(), "get"));
                    add(new LinkDto("entries", "/engines/"+e.getId()+"/entries", "get, put, delete"));
                    if (e.getCar() != null) add(new LinkDto("car", "/cars/" + e.getCar().getId(), "get"));
                }})
                .build();
    }


    public static List<EngineDto> toEngineDtoCollection(Collection<Engine> collection){
        return  collection == null ? Collections.emptyList() : collection.stream()
                .map(EngineDataAssembler::toEngineDto)
                .collect(Collectors.toList());
    }

}
