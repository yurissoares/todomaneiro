package br.com.yuri.todomaneiro.entity.enums;

import lombok.Getter;

@Getter
public enum PerfilUsuario {
    ADMIN("ROLE_ADMIN"),
    VIP("ROLE_VIP"),
    NO_VIP("ROLE_NO_VIP");

    private String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }
}
