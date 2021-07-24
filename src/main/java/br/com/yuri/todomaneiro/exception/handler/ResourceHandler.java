package br.com.yuri.todomaneiro.exception.handler;

import br.com.yuri.todomaneiro.exception.AuthorizationException;
import br.com.yuri.todomaneiro.exception.TechnicalException;
import br.com.yuri.todomaneiro.exception.model.ErrorMapResponse;
import br.com.yuri.todomaneiro.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ResourceHandler {

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ErrorResponse> handlerTechnicalException(TechnicalException t){
        var erroResponse = ErrorResponse.builder()
                .httpStatus(t.getHttpStatus().value())
                .mensagem(t.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(t.getHttpStatus()).body(erroResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m){
        var erros = new HashMap<String, String>();

        m.getBindingResult().getAllErrors().forEach(erro -> {
            var campo = ((FieldError)erro).getField();
            var mensagem = erro.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        var errorMapResponse = ErrorMapResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .erros(erros)
                .timeStamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMapResponse);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handlerAuthorizationException(AuthorizationException a){
        var erroResponse = ErrorResponse.builder()
                .httpStatus(a.getHttpStatus().value())
                .mensagem(a.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(a.getHttpStatus()).body(erroResponse);
    }
}
