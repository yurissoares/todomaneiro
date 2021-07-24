package br.com.yuri.todomaneiro.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class EmailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "Este campo deve ser preenchido.")
    private String email;
}
