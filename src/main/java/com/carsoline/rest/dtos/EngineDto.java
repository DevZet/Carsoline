package com.carsoline.rest.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * Created by Dominik on 2017-04-26.
 */
@Builder
@Data
@Api
@ApiModel(value = "EngineDto")
public class EngineDto{

    @ApiModelProperty("id of engine")
    private Long id;

    @ApiModelProperty("engine size")
    private String engineSize;

    @ApiModelProperty("engine fuel type")
    private String fuelType;

    @ApiModelProperty("engine power")
    private String enginePower;

    @ApiModelProperty("engine symbol")
    private String engineSymbol;

    @ApiModelProperty("id of associated car")
    private Long carId;

    @ApiModelProperty("hateoas links")
    private List<LinkDto> links;

}
