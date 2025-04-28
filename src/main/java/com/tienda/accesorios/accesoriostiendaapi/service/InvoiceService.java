package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.model.Invoice;
import com.tienda.accesorios.accesoriostiendaapi.repository.InvoiceRepository;
import com.tienda.accesorios.accesoriostiendaapi.model.InvoiceItem;
import com.tienda.accesorios.accesoriostiendaapi.repository.InvoiceItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }

    public List<Invoice> listarFacturas() {
        return invoiceRepository.findAll();
    }

    public Invoice obtenerFactura(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con id: " + id));
    }

    public List<InvoiceItem> obtenerItemsFactura(Long invoiceId) {
        return invoiceItemRepository.findByInvoiceId(invoiceId);
    }

    public void eliminarFactura(Long id) {
        // Primero puedes eliminar los items asociados (si no tienes ON DELETE CASCADE)
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceId(id);
        if (!items.isEmpty()) {
            invoiceItemRepository.deleteAll(items);
        }
        // Luego eliminar la factura
        invoiceRepository.deleteById(id);
    }
}