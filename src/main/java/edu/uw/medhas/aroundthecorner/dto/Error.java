package edu.uw.medhas.aroundthecorner.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Error {
    @JsonIgnore
    private HttpStatus httpStatus;
    private Integer errorId;
    private String message;
    
    public Error() {
        setHttpStatus(HttpStatus.OK);
    }
    
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = HttpStatus.valueOf(httpStatus);
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public void setError(HttpStatus httpStatus, Integer errorId, String message) {
        setHttpStatus(httpStatus);
        this.errorId = errorId;
        this.message = message;
    }
    
    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    public Integer getErrorId() {
        return errorId;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
