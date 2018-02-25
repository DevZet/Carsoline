package com.carsoline.rest.dataAssemblers;

import com.carsoline.rest.daos.Entry;
import com.carsoline.rest.dtos.EntryDto;
import com.carsoline.rest.dtos.LinkDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 2017-05-02.
 */
public class EntryDataAssembler {

    public static EntryDto toEntryDto(Entry entry){
        return EntryDto.builder()
                .id(entry.getId())
                .userId(entry.getUser() == null ? null : entry.getUser().getId())
                .engineId(entry.getEngine() == null ? null : entry.getEngine().getId())
                .cityValue(entry.getCityValue() == null ? "" : entry.getCityValue())
                .highwayValue(entry.getHighwayValue() == null ? "" : entry.getHighwayValue())
                .links(new ArrayList<LinkDto>() {{
                    add(new LinkDto("self", "/entries/" + entry.getId(), "get, put, delete"));
                    if (entry.getUser() != null) add(new LinkDto("user", "/users/" + entry.getUser().getId(), "get, put, delete"));
                    if (entry.getEngine() != null) add(new LinkDto( "engine", "/engines/" + entry.getEngine().getId(), "get, put, delete"));
                    if (entry.getEngine() != null) add(new LinkDto( "car", "cars/" + entry.getEngine().getCar().getId(), "get, put, delete"));
                }})
                .build();
    }


    public static List<EntryDto> toEntryDtoCollection(Collection<Entry> collection){
        return  collection == null ? Collections.emptyList() : collection.stream()
                .map(EntryDataAssembler::toEntryDto)
                .collect(Collectors.toList());
    }

}
