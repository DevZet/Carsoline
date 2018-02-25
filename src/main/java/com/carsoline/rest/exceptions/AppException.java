package com.carsoline.rest.exceptions;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Dominik on 2017-05-07.
 */
@Api
@ApiModel(value = "AppException")
public class AppException extends Exception {

    private static final long serialVersionUID = -8999932578270387947L;

    Integer status;

    String developerMessage;

    String mediaType;

    public AppException(int status, String message,
                        String developerMessage, String mediaType) {
        super(message);
        this.status = status;
        this.developerMessage = developerMessage;
        this.mediaType = mediaType;
    }

    public AppException(int status, String message,
                        String developerMessage) {
        super(message);
        this.status = status;
        this.developerMessage = developerMessage;
    }

    public AppException() { }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }




}