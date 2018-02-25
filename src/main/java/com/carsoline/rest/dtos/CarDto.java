package com.carsoline.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import java.time.Year;
import java.util.List;

import javax.xml.bind.annotation.*;


/**
 * Created by Dominik on 2017-04-26.
 */
@Api
@ApiModel(value = "CarDto")
@XmlRootElement
public class CarDto{

    @ApiModelProperty(value = "id of car")
    private Long id;

    @ApiModelProperty(value = "brand of car")
    private String brand;

    @ApiModelProperty(value = "model name of car")
    private String model;

    @ApiModelProperty(value = "Date of car production start")
    private Integer productionStartDate;

    @ApiModelProperty(value = "Car production end date")
    private Integer productionEndDate;

    @ApiModelProperty(value = "Car hateoas links")
    private List<LinkDto> links;

    public CarDto() {}

    @Override
    public String toString() {
        return new StringBuffer(" Brand : ").append(this.brand)
                .append(" Model : ").append(this.model)
                .append(this.id).toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getProductionStartDate() {
        return productionStartDate;
    }

    public void setProductionStartDate(Integer productionStartDate) {
        this.productionStartDate = productionStartDate;
    }

    public Integer getProductionEndDate() {
        return productionEndDate;
    }

    public void setProductionEndDate(Integer productionEndDate) {
        this.productionEndDate = productionEndDate;
    }

    public List<LinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDto> links) {
        this.links = links;
    }
}
