package com.carsoline.rest.exceptions;


import com.mysql.cj.jdbc.exceptions.MySQLQueryInterruptedException;

/**
 * Created by Dominik on 2017-05-29.
 */
public class MySQLJDBCException extends MySQLQueryInterruptedException {

    private static final long serialVersionUID = -8999932578270387947L;

    Integer status;

    int code;

    String developerMessage;

    public MySQLJDBCException(int status, int code, String message,
                        String developerMessage) {
        super(message);
        this.status = status;
        this.code = code;
        this.developerMessage = developerMessage;
    }

    public MySQLJDBCException() { }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

}
