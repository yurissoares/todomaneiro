package br.com.yuri.todomaneiro.controller;

import br.com.yuri.todomaneiro.dto.TodoDto;
import br.com.yuri.todomaneiro.entity.TodoEntity;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/todo")
public class TodoController {

    @Autowired
    private ITodoService todoService;

    @GetMapping
    public ResponseEntity<ResponseModel<List<TodoEntity>>> listar(@RequestParam(value = "isFinalizado", required = false) final Boolean isFinalizado) {
        if(Objects.nonNull(isFinalizado)){
            return ResponseEntity.status(HttpStatus.OK).body(this.todoService.listarPorStatus(isFinalizado));
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.todoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<TodoEntity>> buscar(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.todoService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ResponseModel<TodoEntity>> cadastrar(@Valid @RequestBody final TodoDto todoDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.todoService.cadastrar(todoDto));
    }

    @PutMapping
    public ResponseEntity<ResponseModel<TodoEntity>> atualizar(@Valid @RequestBody final TodoDto todoDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.todoService.atualizar(todoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<Boolean>> excluir(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.todoService.excluir(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<Boolean>> finalizar(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.todoService.finalizar(id));
    }
}
