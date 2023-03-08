package com.erinc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EErrorType {
    MUSTERI_BULUNAMADI(1003,"Aradiginiz musteri kayitli degildir..",INTERNAL_SERVER_ERROR),
    REGISTER_ERROR_PASSWORD_UNMATCH(1004,"Girilen parola uyusmuyor.",BAD_REQUEST),
    REGISTER_ERROR_USERNAME(1005,"Kullanıcı adı daha önce alınmıştır",BAD_REQUEST),
    LOGIN_ERROR_USERNAME_PASSWORD(1006,"Kullanıcı adı yada sifre hatalidir.",BAD_REQUEST),
    INVALID_TOKEN(1007,"Geçersiz token.",BAD_REQUEST),
    URUN_EKLEME(2001,"Urun ekleme basarisiz oldu", INTERNAL_SERVER_ERROR),
    METHOD_MIS_MATCH_ERROR(2002,"Giris yaptiginiz deger, istenilen degere ile uyusmamaktadir.", BAD_REQUEST),
    METHOD_NOT_VALID_ARGUMENT_ERROR(2003,"olmadi..",BAD_REQUEST),
    INVALID_PARAMETER(3001,"Gecersiz parametre gidisi yaptiniz.", BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus httpStatus;
}
