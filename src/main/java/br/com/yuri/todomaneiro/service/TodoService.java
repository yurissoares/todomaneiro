package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.TodoRequestDto;
import br.com.yuri.todomaneiro.dto.TodoResponseDto;
import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.exception.AuthorizationException;
import br.com.yuri.todomaneiro.exception.TechnicalException;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.repository.ITodoRepository;
import br.com.yuri.todomaneiro.repository.IUsuarioRepository;
import br.com.yuri.todomaneiro.security.service.UserService;
import br.com.yuri.todomaneiro.service.email.IEmailService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService implements ITodoService {

    private static final String TODO_NOT_FOUND = "to-do não encontrado.";
    private static final String ERRO_INTERNO = "Erro interno identificado. Contate o suporte.";

    private final ITodoRepository todoRepository;
    private final ModelMapper mapper;

    public TodoService(final ITodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.mapper = new ModelMapper();
    }

    @Autowired
    public IUsuarioRepository usuarioRepository;

    @Autowired
    private IEmailService emailService;

    @Override
    public ResponseModel<List<TodoResponseDto>> listar() {

        var userSS = UserService.authenticated();
        if (userSS == null) {
            throw new AuthorizationException("Acesso negado.", HttpStatus.FORBIDDEN);
        }
        var usuarioEntity = this.usuarioRepository.findById(userSS.getId())
                .orElseThrow(() -> new TechnicalException("Usuário não encontrado.", HttpStatus.NOT_FOUND));

        var response = new ResponseModel<List<TodoResponseDto>>();
        try {
            final var listTodo = this.todoRepository.findByUsuarioEntity(usuarioEntity);
            response.setData(mapper.map(listTodo, new TypeToken<List<TodoResponseDto>>() {}.getType()));
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response.setData(new ArrayList<>());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }
    }

    @Override
    public ResponseModel<TodoResponseDto> buscar(final Long id) {
        var response = new ResponseModel<TodoResponseDto>();
        try {
            final var todoEntity = this.todoRepository.findById(id)
                    .orElseThrow(() -> new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND));

            response.setData(mapper.map(todoEntity, TodoResponseDto.class));
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e) {
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseModel<Boolean> cadastrar(final TodoRequestDto todoRequestDto) {
        var response = new ResponseModel<Boolean>();
        try {
            final var todoEntity = mapper.map(todoRequestDto, TodoEntity.class);
            todoEntity.setCriadoEm(LocalDateTime.now());
            this.todoRepository.save(todoEntity);
            response.setData(Boolean.TRUE);
            response.setStatusCode(HttpStatus.CREATED.value());
            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e) {
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseModel<Boolean> atualizar(final TodoRequestDto todoRequestDto) {
        var response = new ResponseModel<Boolean>();
        try {
            var todoEntityOptional = this.todoRepository.findById(todoRequestDto.getId())
                    .orElseThrow(() -> new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND));

            final var todoEntity = mapper.map(todoRequestDto, TodoEntity.class);
            todoEntity.setCriadoEm(todoEntityOptional.getCriadoEm());
            this.todoRepository.save(todoEntity);
            response.setData(Boolean.TRUE);
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e) {
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseModel<Boolean> excluir(final Long id) {
        var response = new ResponseModel<Boolean>();
        try {
            if (this.todoRepository.findById(id).isPresent()) {
                this.todoRepository.deleteById(id);
                response.setData(Boolean.TRUE);
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e) {
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ResponseModel<Boolean> finalizar(final Long id) {
        var response = new ResponseModel<Boolean>();
        try {
            var todoEntity = this.todoRepository.findById(id)
                    .orElseThrow(() -> new TechnicalException(TODO_NOT_FOUND, HttpStatus.NOT_FOUND));

            todoEntity.setFinalizado(Boolean.TRUE);
            this.todoRepository.save(todoEntity);
            response.setData(Boolean.TRUE);
            response.setStatusCode(HttpStatus.OK.value());

            this.emailService.sendTodoFinalizadoHtmlEmail(todoEntity);

            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e) {
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<List<TodoResponseDto>> listarPorStatus(final Boolean isFinalizado) {
        var response = new ResponseModel<List<TodoResponseDto>>();
        try {
            final var listTodo = this.todoRepository.findAllByFinalizado(isFinalizado);
            response.setData(mapper.map(listTodo, new TypeToken<List<TodoResponseDto>>() {
            }.getType()));
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response.setData(new ArrayList<>());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }
    }
}
