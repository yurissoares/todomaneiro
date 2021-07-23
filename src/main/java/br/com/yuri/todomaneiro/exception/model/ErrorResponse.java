package br.com.yuri.todomaneiro.exception.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private final String mensagem;
    private final int httpStatus;
    private final long timeStamp;

}
