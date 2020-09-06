package io.github.monthalcantara.estudosession.repository;

import io.github.monthalcantara.estudosession.model.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    Boolean existsByLogin(String login);


}
