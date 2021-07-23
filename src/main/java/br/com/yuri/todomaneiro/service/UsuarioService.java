package br.com.yuri.todomaneiro.service;

import br.com.yuri.todomaneiro.dto.UsuarioNewDto;
import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import br.com.yuri.todomaneiro.exception.TechnicalException;
import br.com.yuri.todomaneiro.model.ResponseModel;
import br.com.yuri.todomaneiro.repository.IUsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService{

    private static final String USUARIO_NOT_FOUND = "Usuário não encontrado.";
    private static final String ERRO_INTERNO = "Erro interno identificado. Contate o suporte.";

    private final IUsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    public UsuarioService(final IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = new ModelMapper();
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseModel<List<UsuarioEntity>> listar() {
        var response = new ResponseModel<List<UsuarioEntity>>();
        try {
            response.setData(this.usuarioRepository.findAll());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        } catch(Exception e) {
            response.setData(new ArrayList<>());
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }
    }

    @Override
    public ResponseModel<UsuarioEntity> buscar(final Long id) {
        var response = new ResponseModel<UsuarioEntity>();
        try {
            final var usuarioEntity = this.usuarioRepository.findById(id);
            if(usuarioEntity.isPresent()) {
                response.setData(usuarioEntity.get());
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(USUARIO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<UsuarioEntity> cadastrar(final UsuarioNewDto usuarioNewDto) {
        var response = new ResponseModel<UsuarioEntity>();
        try {
            usuarioNewDto.setSenha(passwordEncoder.encode(usuarioNewDto.getSenha()));
            final var usuarioEntity = this.usuarioRepository.save(mapper.map(usuarioNewDto, UsuarioEntity.class));
            response.setData(usuarioEntity);
            response.setStatusCode(HttpStatus.CREATED.value());
            return response;
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseModel<UsuarioEntity> atualizar(final UsuarioNewDto usuarioNewDto) {
        var response = new ResponseModel<UsuarioEntity>();
        try {
            var usuarioEntity = this.usuarioRepository.findById(usuarioNewDto.getId());
            if(usuarioEntity.isPresent()){
                usuarioNewDto.setSenha(passwordEncoder.encode(usuarioNewDto.getSenha()));
                response.setData(this.usuarioRepository.save(mapper.map(usuarioNewDto, UsuarioEntity.class)));
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(USUARIO_NOT_FOUND, HttpStatus.NOT_FOUND);
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
            if(this.usuarioRepository.findById(id).isPresent()){
                this.usuarioRepository.deleteById(id);
                response.setData(Boolean.TRUE);
                response.setStatusCode(HttpStatus.OK.value());
                return response;
            }
            throw new TechnicalException(USUARIO_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (TechnicalException te) {
            throw te;
        } catch (Exception e){
            throw new TechnicalException(ERRO_INTERNO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
