package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.UsuarioNewDto;
import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import br.com.yuri.todomaneiro.model.ResponseModel;

import java.util.List;

public interface IUsuarioService {
    ResponseModel<List<UsuarioEntity>> listar();
    ResponseModel<UsuarioEntity> buscar(final Long id);
    ResponseModel<UsuarioEntity> cadastrar(final UsuarioNewDto usuarioNewDto);
    ResponseModel<UsuarioEntity> atualizar(final UsuarioNewDto usuarioNewDto);
    ResponseModel<Boolean> excluir(final Long id);
}
