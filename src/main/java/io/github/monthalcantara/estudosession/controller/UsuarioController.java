package io.github.monthalcantara.estudosession.controller;

import io.github.monthalcantara.estudosession.exception.SenhaInvalidaException;
import io.github.monthalcantara.estudosession.jwt.JwtService;
import io.github.monthalcantara.estudosession.model.TokenDTO;
import io.github.monthalcantara.estudosession.model.Usuario;
import io.github.monthalcantara.estudosession.service.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private JwtService jwtService;


    @Value("${contato.disco.raiz}")
    private String raiz;

    @Value("${contato.disco.diretorio-fotos}")
    private String diretorioArquivos;

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

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam MultipartFile image) {

        return salvar(diretorioArquivos, image);
    }

    private String salvar(String diretorio, MultipartFile arquivo) {
        Path diretorioPath = Paths.get(raiz, diretorio);
        Path arquivoPath = diretorioPath.resolve(arquivo.getOriginalFilename());

        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo: ", e);
        }
        return String.valueOf(arquivoPath);
    }
}



