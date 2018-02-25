package com.carsoline.rest.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Dominik on 2017-05-06.
 */
@Builder
@Data
@Api
@ApiModel(value = "UserDto")
public class UserDto {

    @ApiModelProperty("user id")
    private String id;

    @ApiModelProperty("user name")
    private String name;

    @ApiModelProperty("user email")
    private String email;

    @ApiModelProperty("user password")
    private String password;

    @ApiModelProperty("user role (authorization)")
    private String role;

    @ApiModelProperty("hateoas links")
    private List<LinkDto> links;

    @ApiModelProperty(value = "Enabled status of user")
    private Short enabled;

}
