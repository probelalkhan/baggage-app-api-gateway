package com.siemens.sl.apigateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.sl.apigateway.controller.AuthenticationController;
import com.siemens.sl.apigateway.exception.customexceptions.GenericException;
import com.siemens.sl.apigateway.model.ErrorResponse;
import com.siemens.sl.apigateway.utility.ErrorJsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    private static Logger logger = LogManager.getLogger(AuthenticationController.class);

    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        logger.error("Caught BadCredentialsException!!");
        ErrorResponse responseBody = ErrorJsonParser.getErrorResponse("en", 401, 18);
        logger.error("Error response returned for the request");
        return handleExceptionInternal(ex, responseBody,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {GenericException.class})
    protected ResponseEntity<Object> handleGenericException(RuntimeException ex, WebRequest request) {

        GenericException exception = ((GenericException) ex);

        ErrorResponse responseBody;
        if (exception.getErrorBody() != null)
            responseBody = exception.getErrorBody();
        else
            responseBody = ErrorJsonParser.getErrorResponse("en", exception.getCode(), exception.getSubCode());

        try {
            logger.error("Caught generic exception with title: "+responseBody.getErrorTitle());
        } catch (Exception e) {
            logger.error("Caught generic exception!!");
        }

        logger.error("Error response returned for the request");
        return handleExceptionInternal(exception, responseBody,
                new HttpHeaders(), exception.getErrorCode(), request);
    }
}