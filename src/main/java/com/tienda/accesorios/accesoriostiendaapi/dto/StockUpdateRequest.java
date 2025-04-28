package com.tienda.accesorios.accesoriostiendaapi.dto;

public class StockUpdateRequest {
    private String action; // "add", "remove" o "set"
    private int amount;
    private String reason;

    // Getters y Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
