package edu.uw.medhas.aroundthecorner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import edu.uw.medhas.aroundthecorner.exception.AppException;

@ControllerAdvice
public class ControllerException {
    private static final Logger logger = LoggerFactory.getLogger(ControllerException.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        String errorMsg = "Something went wrong, please contact support team immediately";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (ex instanceof TypeMismatchException
                || ex instanceof IllegalArgumentException) {
            errorMsg = "Request has invalid arguments in path or query or body";
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageConversionException) {
            errorMsg = "Request has invalid JSON in body";
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NoHandlerFoundException) {
            errorMsg = "No handler found for incoming request";
            httpStatus = HttpStatus.NOT_FOUND;
        } else if(ex instanceof HttpMediaTypeException) {
            errorMsg = "System doesn't support the content type in your request";
            httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            errorMsg = "System doesn't support the requested method";
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof AppException) {
            AppException aex = (AppException) ex;
            if (HttpStatus.valueOf(aex.getErrorId()) != null) {
                httpStatus = HttpStatus.valueOf(aex.getErrorId());
            }
            errorMsg = aex.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        
        return new ResponseEntity<>(errorMsg, httpStatus);
    }
}
