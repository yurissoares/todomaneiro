package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.TodoDto;
import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.exception.TechnicalException;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.repository.ITodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService implements ITodoService{

    private static final String TODO_NOT_FOUND = "to-do não encontrado.";
    private static final String ERRO_INTERNO = "Erro interno identificado. Contate o suporte.";

    private final ITodoRepository todoRepository;
    private final ModelMapper mapper;

    public TodoService(final ITodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public ResponseModel<List<TodoEntity>> listar() {
        var response = new ResponseModel<List<TodoEntity>>();
        try {
            response.setData(this.todoRepository.findAll());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch(Exception e) {
            response.setData(new ArrayList<>());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }
    }

    @Override
    public ResponseModel<TodoEntity> buscar(final Long id) {
        var response = new ResponseModel<TodoEntity>();
        try {
            final var todoEntity = this.todoRepository.findById(id);
            if(todoEntity.isPresent()) {
                response.setData(todoEntity.get());
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<TodoEntity> cadastrar(final TodoDto todoDto) {
        var response = new ResponseModel<TodoEntity>();
        try {
            todoDto.setFinalizado(Boolean.FALSE);
            final var todoEntity = this.todoRepository.save(mapper.map(todoDto, TodoEntity.class));
            response.setData(todoEntity);
            response.setStatusCode(HttpStatus.CREATED.value());
            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<TodoEntity> atualizar(final TodoDto todoDto) {
        var response = new ResponseModel<TodoEntity>();
        try {
            if(this.todoRepository.findById(todoDto.getId()).isPresent()){
                response.setData(this.todoRepository.save(mapper.map(todoDto, TodoEntity.class)));
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<Boolean> excluir(final Long id) {
        var response = new ResponseModel<Boolean>();
        try {
            if(this.todoRepository.findById(id).isPresent()){
                this.todoRepository.deleteById(id);
                response.setData(Boolean.TRUE);
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<Boolean> finalizar(final Long id) {
        var response = new ResponseModel<Boolean>();
        try {
            var todoEntity = this.todoRepository.findById(id);
            if(todoEntity.isPresent()){
                todoEntity.get().setFinalizado(Boolean.TRUE);
                this.todoRepository.save(todoEntity.get());
                response.setData(Boolean.TRUE);
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<List<TodoEntity>> listarPorStatus(final Boolean isFinalizado) {
        var response = new ResponseModel<List<TodoEntity>>();
        try {
            response.setData(this.todoRepository.findAllByFinalizado(isFinalizado));
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch(Exception e) {
            response.setData(new ArrayList<>());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }
    }
}
