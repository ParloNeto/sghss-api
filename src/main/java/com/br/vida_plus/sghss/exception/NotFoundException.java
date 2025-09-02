package com.br.vida_plus.sghss.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg + " não encontrado");
    }

    public NotFoundException() {
        super("Objeto não encontrado");
    }
}
