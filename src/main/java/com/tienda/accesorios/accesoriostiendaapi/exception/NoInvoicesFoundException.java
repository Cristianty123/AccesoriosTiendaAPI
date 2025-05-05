package com.tienda.accesorios.accesoriostiendaapi.exception;

public class NoInvoicesFoundException extends RuntimeException {
    public NoInvoicesFoundException(String message) {
        super(message);
    }
}
