package com.erinc.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Tüm istisnaların üzeründen geçtiği bir method oluşturuyorum ve hatta mesajını burada dönüyorum.
     */

    private ErrorMessage createErrorMessage(EErrorType errorType, Exception exception){
        System.out.println("Hata olustu....: " + exception.getMessage());
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    /**
     * @ExceptionHandler -> Uygulama içinde oluşacak hatanın türünü bizden alarak,
     * bizim yerimize yakalamasını sağlar.
     * Böylece yakaladığı istisnayı method'a geçer.
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handlerException(Exception e){
        return ResponseEntity.badRequest().body("Uygulamada beklenmeyen bir hata olustu....: "+e.getMessage() );
    }

    @ResponseBody
    @ExceptionHandler(AuthServiceException.class)
    public ResponseEntity<ErrorMessage> handleSatisManagerException(AuthServiceException e){
        EErrorType errorType =e.getErrorType();
        HttpStatus httpStatus = errorType.getHttpStatus();
        return new ResponseEntity<>(createErrorMessage(errorType,e),httpStatus);

    }

    @ResponseBody
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorMessage> handleInvalidFormatException(Exception exception){
        EErrorType errorType = EErrorType.INVALID_PARAMETER;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        EErrorType errorType = EErrorType.METHOD_MIS_MATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        EErrorType errorType = EErrorType.METHOD_MIS_MATCH_ERROR;
        return new ResponseEntity<>(createErrorMessage(errorType,exception),errorType.getHttpStatus());
    }
    /**
     * http://localhost:9090/urun/findbyid/234/sort/desc
     * http://localhost:9090/urun/findbyid/sort/desc
     */




}
