package br.com.yuri.todomaneiro.dto;

import br.com.yuri.todomaneiro.entity.enums.PerfilUsuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class UsuarioResponseDto implements Serializable {

    private Long id;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String nomeCompleto;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Este campo deve ser preenchido.")
    private String email;

    @NotNull(message = "Perfil é obrigatório.")
    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;
}
