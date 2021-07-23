package br.com.yuri.todomaneiro.repository;

import br.com.yuri.todomaneiro.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
