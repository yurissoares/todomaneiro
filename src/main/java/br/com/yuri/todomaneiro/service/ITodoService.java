package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.TodoDto;
import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.model.ResponseModel;

import java.util.List;

public interface ITodoService {
    ResponseModel<List<TodoEntity>> listar();
    ResponseModel<TodoEntity> buscar(final Long id);
    ResponseModel<TodoEntity> cadastrar(final TodoDto todoDto);
    ResponseModel<TodoEntity> atualizar(final TodoDto todoDto);
    ResponseModel<Boolean> excluir(final Long id);
    ResponseModel<Boolean> finalizar(final Long id);
    ResponseModel<List<TodoEntity>> listarPorStatus(final Boolean isFinalizado);
}
