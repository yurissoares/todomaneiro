package br.com.yuri.todomaneiro.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseModel<T> {

    private int statusCode;
    private T data;
    private long timeStamp;

    public ResponseModel(){
        this.timeStamp = System.currentTimeMillis();
    }
}
