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

@Getter
@Setter
@RequiredArgsConstructor
public class UsuarioRequestDto {

    private Long id;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String nomeCompleto;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Este campo deve ser preenchido.")
    private String email;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String senha;

    @NotNull(message = "Perfil é obrigatório.")
    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;
}
