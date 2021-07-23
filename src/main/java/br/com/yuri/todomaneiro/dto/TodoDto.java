package br.com.yuri.todomaneiro.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
@RequiredArgsConstructor
public class TodoDto {

    private Long id;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String descricao;

    @Null(message = "Este campo não deve ser preenchido.")
    private Boolean finalizado;

}
