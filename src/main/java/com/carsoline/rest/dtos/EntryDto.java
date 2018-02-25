package com.carsoline.rest.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Dominik on 2017-05-02.
 */
@Builder
@Data
@Api
@ApiModel(value = "EntryDto")
public class EntryDto {

    @ApiModelProperty("entry id")
    private String id;

    @ApiModelProperty("id of associated user")
    private String userId;

    @ApiModelProperty("id of associated engine")
    private Long engineId;

    @ApiModelProperty("highway fuel usage value")
    private String highwayValue;

    @ApiModelProperty("city fuel usage value")
    private String cityValue;

    @ApiModelProperty("hateoas links")
    private List<LinkDto> links;
}
