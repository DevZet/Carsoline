package com.carsoline.rest.dataAssemblers;

import com.carsoline.rest.daos.User;
import com.carsoline.rest.dtos.LinkDto;
import com.carsoline.rest.dtos.UserDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dominik on 2017-05-06.
 */
public class UserDataAssembler {

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName() == null ? "" : user.getName())
                .email(user.getEmail() == null ? "" : user.getEmail())
                .password("[hidden]")
                .role(user.getRole() == null ? "" : user.getRole())
                .enabled(user.getEnabled())
                .links(new ArrayList<LinkDto>() {{
                    add(new LinkDto("self", "/users/" + user.getId(), "get, put, delete"));
                }})
                .build();
    }


    public static List<UserDto> toUserDtoCollection(Collection<User> collection){
        return  collection == null ? Collections.emptyList() : collection.stream()
                .map(UserDataAssembler::toUserDto)
                .collect(Collectors.toList());
    }

}
