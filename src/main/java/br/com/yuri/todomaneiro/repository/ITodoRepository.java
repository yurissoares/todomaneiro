package br.com.yuri.todomaneiro.repository;

import br.com.yuri.todomaneiro.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByFinalizado(final Boolean isFinalizado);
}
