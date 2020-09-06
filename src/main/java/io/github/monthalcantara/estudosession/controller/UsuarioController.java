package io.github.monthalcantara.estudosession.controller;

import io.github.monthalcantara.estudosession.exception.SenhaInvalidaException;
import io.github.monthalcantara.estudosession.jwt.JwtService;
import io.github.monthalcantara.estudosession.model.TokenDTO;
import io.github.monthalcantara.estudosession.model.Usuario;
import io.github.monthalcantara.estudosession.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody Usuario userLogin, HttpSession session) {
        try {
            Usuario user = Usuario.builder()
                    .login(userLogin.getLogin())
                    .password(userLogin.getPassword())
                    .build();
            UserDetails authenticate = userService.autenticar(user);
            String token = jwtService.createToken(user);
            return new TokenDTO(userLogin.getLogin(), token);


        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    }


