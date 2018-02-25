package com.carsoline.rest.exceptions;


import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by Dominik on 2017-05-07.
 */
@Api
@ApiModel(value = "ErrorMessage")
public class ErrorMessage {

    int status;

    String message;

    String developerMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public ErrorMessage(AppException ex){
        try {
            BeanUtils.copyProperties(this, ex);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ErrorMessage(NotFoundException ex){
        this.status = Response.Status.NOT_FOUND.getStatusCode();
        this.message = ex.getMessage();
    }

    public ErrorMessage() {}

}
