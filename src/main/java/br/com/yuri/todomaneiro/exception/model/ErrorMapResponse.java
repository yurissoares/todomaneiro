package br.com.yuri.todomaneiro.exception.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class ErrorMapResponse {
    private final int httpStatus;
    private final Map<String, String> erros;
    private final Long timeStamp;
}
