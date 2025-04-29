package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InvoiceTaxId implements Serializable {

    private Integer invoiceId;
    private Integer taxId;

    public InvoiceTaxId() {}

    public InvoiceTaxId(Integer invoiceId, Integer taxId) {
        this.invoiceId = invoiceId;
        this.taxId = taxId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceTaxId that)) return false;
        return Objects.equals(invoiceId, that.invoiceId) &&
                Objects.equals(taxId, that.taxId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, taxId);
    }
}
