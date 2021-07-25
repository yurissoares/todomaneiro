package br.com.yuri.todomaneiro.security.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class CredenciaisDto implements Serializable {
    private String email;
    private String senha;
}
