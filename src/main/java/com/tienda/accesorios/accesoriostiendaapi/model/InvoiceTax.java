package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "invoice_tax")
public class InvoiceTax {

    @EmbeddedId
    private InvoiceTaxId id;

    @ManyToOne
    @MapsId("invoiceId")
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @MapsId("taxId")
    @JoinColumn(name = "tax_id")
    private Tax tax;

    public InvoiceTax() {}

    public InvoiceTax(Invoice invoice, Tax tax) {
        this.invoice = invoice;
        this.tax = tax;
        this.id = new InvoiceTaxId(invoice.getId(), tax.getId());
    }

    public InvoiceTaxId getId() {
        return id;
    }

    public void setId(InvoiceTaxId id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
}
