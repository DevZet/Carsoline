package com.carsoline.rest.daos;

import com.carsoline.rest.dtos.CarDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.time.Year;

/**
 * Created by Dominik on 2017-04-12.
 */

@Entity(name = "car")
@Api
@ApiModel(value = "Car")
@XmlRootElement
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "productionStartDate")
    private Integer productionStartDate;

    @Column(name = "productionEndDate")
    private Integer productionEndDate;

    @OneToMany(mappedBy = "car", targetEntity = Engine.class, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Collection<Engine> engines;

    public Car() {}

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

    public Collection<Engine> getEngines() {
        return engines;
    }

    public void setEngines(Collection<Engine> engines) {
        this.engines = engines;
    }

}
