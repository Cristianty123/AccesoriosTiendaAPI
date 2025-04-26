package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.model.Invoice;
import com.tienda.accesorios.accesoriostiendaapi.model.InvoiceItem;
import com.tienda.accesorios.accesoriostiendaapi.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<Invoice> listarFacturas() {
        return invoiceService.listarFacturas();
    }

    @GetMapping("/{id}")
    public Invoice obtenerFactura(@PathVariable Long id) {
        return invoiceService.obtenerFactura(id);
    }

    @GetMapping("/{id}/items")
    public List<InvoiceItem> obtenerItemsFactura(@PathVariable Long id) {
        return invoiceService.obtenerItemsFactura(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarFactura(@PathVariable Long id) {
        invoiceService.eliminarFactura(id);
    }

}
