package com.saida.register_customer.error;

import com.saida.register_customer.config.MessageGenerator;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageGenerator messageGenerator;

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestErrorResponse handleAccountNotFoundException(CustomerNotFoundException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("CustomerNotFound exception, uuid: {}, message: {}", uuid, ex.getMessage());
        return new RestErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), uuid);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handleDataIntegrityViolation(ServiceException ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("service exception: {}", ex.getMessage());
        return new RestErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), uuid);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String errorMessage = messageGenerator.getMessage(ErrorMessage.DUPLICATE_ACCOUNT_NUMBER);
        String uuid = UUID.randomUUID().toString();
        log.error("DataIntegrityViolationException exception: {}", ex.getMessage());
        return new RestErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), uuid);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestErrorResponse handleException(Exception ex) {
        String uuid = UUID.randomUUID().toString();
        log.error("Internal server error, uuid: {}, message: {}", uuid, ex.getMessage());
        return new RestErrorResponse(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.value(), uuid);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public RestErrorResponse handleFeignException(FeignException ex) {
        log.error("Client exception happened: {} {}", ex.getMessage(), ex.status());
        RestErrorResponse response;
        String uid = UUID.randomUUID().toString();
        if (ex instanceof RetryableException &&
                ex.getCause() instanceof SocketTimeoutException) {
            response = new RestErrorResponse("Service provider read timeout", INTERNAL_SERVER_ERROR.value(), uid);
        } else if (ex instanceof RetryableException &&
                ex.getCause() instanceof ConnectException) {
            response = new RestErrorResponse("Service provider connect timeout", INTERNAL_SERVER_ERROR.value(), uid);
        } else {
            response = new RestErrorResponse("Service provider error", INTERNAL_SERVER_ERROR.value(), uid);
        }
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Type MisMatch exception:{}", ex.getMessage());
        RestErrorResponse response =
                new RestErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), uuid);

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Method not supported,exception:{}", ex.getMessage());
        RestErrorResponse response =
                new RestErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.name(), HttpStatus.METHOD_NOT_ALLOWED.value(), uuid);
        return new ResponseEntity<>(response, headers, HttpStatus.METHOD_NOT_ALLOWED);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        log.error("Http Message Not Readable Exception, message:{}", ex.getMessage());
        String errorMessage = ex.getMessage();
        Throwable cause = ex.getCause();
        System.out.println("cause:" + cause.getMessage());
        if (cause != null && cause.getMessage() != null && ex.getMessage().contains("JSON parse error")) {
            errorMessage = messageGenerator.getMessage(ErrorMessage.INVALID_ENUM_VALUE);
        }
        RestErrorResponse response =
                new RestErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), uuid);
        return new ResponseEntity<>(response, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String uuid = UUID.randomUUID().toString();
        Set<String> errorMessages = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = null;
            String errorMessage = null;
            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
                errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + " " + errorMessage);
            } else if (error != null) {
                fieldName = (error).getObjectName();
                errorMessage = error.getDefaultMessage();
                errorMessages.add(fieldName + " " + errorMessage);
            }
            log.error("Validation exception fieldName: {} , message: {}", fieldName, errorMessage);
        });
        RestErrorResponse response = new RestErrorResponse(HttpStatus.BAD_REQUEST.name(), HttpStatus.BAD_REQUEST.value(), uuid);
        return new ResponseEntity<>(response, headers, status);
    }

}
