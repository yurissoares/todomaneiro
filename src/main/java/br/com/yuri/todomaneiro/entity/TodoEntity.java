package br.com.yuri.todomaneiro.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TODO")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "description", nullable = false)
    private String descricao;

    @Column(name = "finished")
    private Boolean finalizado;

}
