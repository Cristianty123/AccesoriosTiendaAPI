package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.InvoiceResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.Invoice;
import com.tienda.accesorios.accesoriostiendaapi.model.InvoiceItem;
import com.tienda.accesorios.accesoriostiendaapi.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ObjectMapper objectMapper;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        this.objectMapper = new ObjectMapper();
    }
    @GetMapping
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<List<InvoiceResponse>> obtenerFacturas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String buscar,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamaño) {

        List<InvoiceResponse> facturas = invoiceService.obtenerFacturas(estado, clienteId, fechaDesde, fechaHasta, buscar, pagina, tamaño);
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<InvoiceResponse> obtenerFacturaPorId(@PathVariable Long id) {
        InvoiceResponse factura = facturaService.obtenerFacturaPorId(id);
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/{id}/pdf")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<byte[]> generarPdfFactura(@PathVariable Long id) {
        byte[] pdf = facturaService.generarPdf(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura-" + id + ".pdf")
                .body(pdf);
    }

    @GetMapping("/{id}/imprimir")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<byte[]> imprimirFactura(@PathVariable Long id) {
        byte[] datos = facturaService.obtenerDatosImprimibles(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=imprimir-factura-" + id + ".txt")
                .body(datos);
    }

    @PutMapping("/{id}/estado")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> actualizarEstadoFactura(
            @PathVariable Long id,
            @RequestBody EstadoFacturaRequest estadoRequest) {
        facturaService.actualizarEstado(id, estadoRequest.getNuevoEstado());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/recordatorio")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> enviarRecordatorio(@PathVariable Long id) {
        facturaService.enviarRecordatorio(id);
        return ResponseEntity.ok().build();
    }

}
