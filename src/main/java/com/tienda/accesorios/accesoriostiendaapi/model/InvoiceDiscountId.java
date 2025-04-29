package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceDiscountId implements Serializable {

    private Integer invoiceId;
    private Integer discountId;

    // Constructor vacío
    public InvoiceDiscountId() {}

    public InvoiceDiscountId(Integer invoiceId, Integer discountId) {
        this.invoiceId = invoiceId;
        this.discountId = discountId;
    }

    // Getters y setters
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceDiscountId that)) return false;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(discountId, that.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, discountId);
    }
}
