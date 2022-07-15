package uz.fido_biznes.rest.advices;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.fido_biznes.rest.exceptions.ServiceException;

import java.sql.SQLException;

@RestControllerAdvice
public class ServiceAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request){
        String body = "Test Exception Body!!!!!";
        //Response response = new Response();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);// new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(value = {SQLException.class})
    protected ResponseEntity<Object> handleSqlException(SQLException ex, WebRequest request){
        String body = "This is SqlException! = " + ex.toString();
        //Response response = new Response();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);// new ResponseEntity<>(response, HttpStatus.OK);
        //return  new ResponseEntity<>(body, HttpStatus.OK);
    }
    /*@ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request){
        String body = "Test Exception Body!";
        //Response response = new Response();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);// new ResponseEntity<>(response, HttpStatus.OK);
    }*/
    @ExceptionHandler(value = {WebClientResponseException.class})
    protected ResponseEntity<Object> handleWebClientResponseException(WebClientResponseException ex, WebRequest request){
        String body = "Test Exception Body!";
        //Response response = new Response();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);// new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request){
        String body = "This is SqlException! = " + ex.toString();
        //Response response = new Response();
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);// new ResponseEntity<>(response, HttpStatus.OK);
    }


}
