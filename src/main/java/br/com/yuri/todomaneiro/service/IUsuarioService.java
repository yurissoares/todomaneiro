package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.UsuarioResponseDto;
import br.com.yuri.todomaneiro.dto.UsuarioRequestDto;
import br.com.yuri.todomaneiro.model.ResponseModel;

import java.util.List;

public interface IUsuarioService {
    ResponseModel<List<UsuarioResponseDto>> listar();
    ResponseModel<UsuarioResponseDto> buscar(final Long id);
    ResponseModel<Boolean> cadastrar(final UsuarioRequestDto usuarioRequestDto);
    ResponseModel<Boolean> atualizar(final UsuarioRequestDto usuarioRequestDto);
    ResponseModel<Boolean> excluir(final Long id);
}
