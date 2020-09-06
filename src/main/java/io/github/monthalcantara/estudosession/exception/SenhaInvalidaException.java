package io.github.monthalcantara.estudosession.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha Inválida");
    }
}
