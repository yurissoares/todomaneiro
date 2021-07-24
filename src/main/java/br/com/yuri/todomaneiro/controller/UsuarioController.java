package br.com.yuri.todomaneiro.controller;

import br.com.yuri.todomaneiro.dto.UsuarioResponseDto;
import br.com.yuri.todomaneiro.dto.UsuarioRequestDto;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseModel<List<UsuarioResponseDto>>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<UsuarioResponseDto>> buscar(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ResponseModel<Boolean>> cadastrar(@Valid @RequestBody final UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioService.cadastrar(usuarioRequestDto));
    }

    @PutMapping
    public ResponseEntity<ResponseModel<Boolean>> atualizar(@Valid @RequestBody final UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.atualizar(usuarioRequestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<Boolean>> excluir(@PathVariable final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.excluir(id));
    }
}
