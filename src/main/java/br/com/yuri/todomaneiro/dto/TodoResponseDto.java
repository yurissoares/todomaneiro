package br.com.yuri.todomaneiro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class TodoResponseDto {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime criadoEm;

    @NotBlank(message = "Este campo deve ser preenchido.")
    private String descricao;

    @Null(message = "Este campo não deve ser preenchido.")
    private Boolean finalizado;

    @NotNull(message = "Este campo deve ser preenchido.")
    private Long usuarioId;
}
