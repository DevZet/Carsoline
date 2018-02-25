package com.carsoline.rest.daos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by Dominik on 2017-04-25.
 */
@Entity(name = "entry")
@Api
@ApiModel(value = "Entry")
public class Entry {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "city_value")
    private String cityValue;

    @Column(name = "highway_value")
    private String highwayValue;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityValue() {
        return cityValue;
    }

    public void setCityValue(String cityValue) {
        this.cityValue = cityValue;
    }

    public String getHighwayValue() {
        return highwayValue;
    }

    public void setHighwayValue(String highwayValue) {
        this.highwayValue = highwayValue;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
