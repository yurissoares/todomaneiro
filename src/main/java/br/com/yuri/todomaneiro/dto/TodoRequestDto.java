package br.com.yuri.todomaneiro.dto;

import br.com.yuri.todomaneiro.entity.UsuarioEntity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@RequiredArgsConstructor
public class TodoRequestDto {

    private Long id;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String descricao;

    @Null(message = "Este campo não deve ser preenchido.")
    private Boolean finalizado;

    @NotNull(message = "Este campo deve ser preenchido.")
    private UsuarioEntity usuario;
}
