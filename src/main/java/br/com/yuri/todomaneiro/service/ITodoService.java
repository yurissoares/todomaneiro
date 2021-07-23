package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.TodoRequestDto;
import br.com.yuri.todomaneiro.dto.TodoResponseDto;
import br.com.yuri.todomaneiro.model.ResponseModel;

import java.util.List;

public interface ITodoService {
    ResponseModel<List<TodoResponseDto>> listar();
    ResponseModel<TodoResponseDto> buscar(final Long id);
    ResponseModel<Boolean> cadastrar(final TodoRequestDto todoRequestDto);
    ResponseModel<Boolean> atualizar(final TodoRequestDto todoRequestDto);
    ResponseModel<Boolean> excluir(final Long id);
    ResponseModel<Boolean> finalizar(final Long id);
    ResponseModel<List<TodoResponseDto>> listarPorStatus(final Boolean isFinalizado);
}
