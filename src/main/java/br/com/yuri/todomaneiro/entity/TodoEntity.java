package br.com.yuri.todomaneiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "TODO")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "description", nullable = false)
    private String descricao;

    @Column(name = "finished")
    private Boolean finalizado;

    @ManyToOne
    @JoinColumn(name = "USUARIO_id", nullable = false)
    private UsuarioEntity usuarioEntity;

}
