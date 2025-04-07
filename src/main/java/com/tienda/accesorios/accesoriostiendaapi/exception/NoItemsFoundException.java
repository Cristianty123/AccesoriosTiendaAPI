package com.tienda.accesorios.accesoriostiendaapi.exception;

public class NoItemsFoundException extends RuntimeException {
    public NoItemsFoundException(String message) {
        super(message);
    }
}