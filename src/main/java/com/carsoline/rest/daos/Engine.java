package com.carsoline.rest.daos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import java.util.Collection;

/**
 * Created by Dominik on 2017-04-12.
 */
@Entity(name = "engine")
@Api
@ApiModel(value = "Engine")
public class Engine {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id", required = true)
    private Long id;

    @Column(name = "engine_size")
    @ApiModelProperty(value = "Engine size", required = true)
    private String engineSize;

    @Column(name = "fuel_type")
    @ApiModelProperty(value = "Fuel type", required = true)
    private String fuelType;

    @Column(name = "engine_power")
    @ApiModelProperty(value = "Engine power", required = true)
    private String enginePower;

    @Column(name = "engine_symbol")
    @ApiModelProperty(value = "Engine symbol", required = true)
    private String engineSymbol;

    @OneToMany(mappedBy = "engine", targetEntity = Entry.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ApiModelProperty
    private Collection<Entry> entries;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @ApiModelProperty
    private Car car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getEngineSymbol() {
        return engineSymbol;
    }

    public void setEngineSymbol(String engineSymbol) {
        this.engineSymbol = engineSymbol;
    }

    public Collection<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Collection<Entry> entries) {
        this.entries = entries;
    }
}
