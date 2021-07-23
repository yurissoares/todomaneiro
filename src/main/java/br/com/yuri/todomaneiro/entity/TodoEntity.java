package br.com.yuri.todomaneiro.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "TODO")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(name = "description")
    private String descricao;

    @Column(name = "finished")
    private Boolean finalizado;

}
