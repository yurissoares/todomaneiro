package br.com.yuri.todomaneiro.controller;

import br.com.yuri.todomaneiro.dto.UsuarioNewDto;
import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ResponseModel<List<UsuarioEntity>>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<UsuarioEntity>> buscar(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ResponseModel<UsuarioEntity>> cadastrar(@Valid @RequestBody final UsuarioNewDto usuarioNewDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioService.cadastrar(usuarioNewDto));
    }

    @PutMapping
    public ResponseEntity<ResponseModel<UsuarioEntity>> atualizar(@Valid @RequestBody final UsuarioNewDto usuarioNewDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.atualizar(usuarioNewDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<Boolean>> excluir(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.excluir(id));
    }
}
