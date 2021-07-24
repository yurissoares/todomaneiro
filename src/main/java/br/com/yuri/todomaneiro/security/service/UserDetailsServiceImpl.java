package br.com.yuri.todomaneiro.security.service;

import br.com.yuri.todomaneiro.entity.enums.PerfilUsuario;
import br.com.yuri.todomaneiro.repository.IUsuarioRepository;
import br.com.yuri.todomaneiro.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        var usuarioEntity = this.usuarioRepository.findByEmail(email);
        if(!usuarioEntity.isPresent()) {
            throw new UsernameNotFoundException(email);
        }

        var setPerfis = new HashSet<PerfilUsuario>();
        setPerfis.add(usuarioEntity.get().getPerfil());
        return new UserSS(usuarioEntity.get().getId(), usuarioEntity.get().getEmail(), usuarioEntity.get().getSenha(), setPerfis);
    }
}
