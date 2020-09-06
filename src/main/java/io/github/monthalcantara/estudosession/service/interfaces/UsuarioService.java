package io.github.monthalcantara.estudosession.service.interfaces;

import io.github.monthalcantara.estudosession.model.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService {


    UserDetails autenticar(Usuario usuario);

    boolean existsByLogin(String login);
}
