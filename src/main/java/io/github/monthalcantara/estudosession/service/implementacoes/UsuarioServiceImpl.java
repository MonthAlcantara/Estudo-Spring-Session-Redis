package io.github.monthalcantara.estudosession.service.implementacoes;


import io.github.monthalcantara.estudosession.exception.SenhaInvalidaException;
import io.github.monthalcantara.estudosession.model.Usuario;
import io.github.monthalcantara.estudosession.repository.UsuarioRepository;
import io.github.monthalcantara.estudosession.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean isEquals = passwordEncoder.matches(usuario.getPassword(), userDetails.getPassword());
        if (isEquals) {
            return userDetails;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        String[] roles = usuario.isAdmin() ?
                new String[]{"USER", "ADMIN"} :
                new String[]{"USER"};

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }

}
