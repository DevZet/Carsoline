package com.carsoline.rest.dtos;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Dominik on 2017-05-02.
 */
@Api
@ApiModel(value = "LinkDto")
public class LinkDto {

    @ApiModelProperty("rel")
    private String rel;

    @ApiModelProperty("href")
    private String href;

    @ApiModelProperty("method")
    private String method;

    public LinkDto(String rel, String href, String method) {
        this.rel = rel;
        this.href = href;
        this.method = method;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
