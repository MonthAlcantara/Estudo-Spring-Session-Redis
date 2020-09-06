package io.github.monthalcantara.estudosession.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/session")
public class SessionController {

    @GetMapping
    public String helloWorld() {
        return "Você está logado na aplicação";
    }
}
