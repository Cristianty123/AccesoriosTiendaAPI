package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceItemId implements Serializable {

    private Integer invoiceId;
    private String itemId;

    // --- Constructores ---
    public InvoiceItemId() {
    }

    public InvoiceItemId(Integer invoiceId, String itemId) {
        this.invoiceId = invoiceId;
        this.itemId = itemId;
    }

    // --- Getters y Setters ---S
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    // --- equals y hashCode ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceItemId)) return false;
        InvoiceItemId that = (InvoiceItemId) o;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, itemId);
    }
}

