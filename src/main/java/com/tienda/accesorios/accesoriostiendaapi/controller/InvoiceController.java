package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.InvoiceResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.Invoice;
import com.tienda.accesorios.accesoriostiendaapi.model.PurchaseOrder;
import com.tienda.accesorios.accesoriostiendaapi.repository.PurchaseOrderRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ObjectMapper objectMapper;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        this.objectMapper = new ObjectMapper();
    }

    // Obtener todas las facturas con filtros opcionales
    @GetMapping(("/getInvoice"))
    public ResponseEntity<List<InvoiceResponse>> obtenerFacturas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @RequestParam(required = false) String buscar,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamaño) {

        List<InvoiceResponse> facturas = invoiceService.obtenerFacturas(
                estado, clienteId, fechaDesde, fechaHasta, buscar, pagina, tamaño
        );
        return ResponseEntity.ok(facturas);
    }

    // Obtener una factura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> obtenerFacturaPorId(@PathVariable Integer id) {
        InvoiceResponse factura = invoiceService.obtenerFacturaPorId(id);
        return ResponseEntity.ok(factura);
    }

    // Descargar PDF de una factura
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> generarPdfFactura(@PathVariable Integer id) {
        byte[] pdf = invoiceService.generarPdf(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura-" + id + ".pdf")
                .body(pdf);
    }

    // Imprimir datos de factura (formato txt simulado)
    @GetMapping("/{id}/imprimir")
    public ResponseEntity<byte[]> imprimirFactura(@PathVariable Integer id) {
        byte[] datos = invoiceService.obtenerDatosImprimibles(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=imprimir-factura-" + id + ".txt")
                .body(datos);
    }

    // Enviar recordatorio (ej. correo, log, etc.)
    @PostMapping("/{id}/recordatorio")
    public ResponseEntity<Void> enviarRecordatorio(@PathVariable Integer id) {
        invoiceService.enviarRecordatorio(id);
        return ResponseEntity.ok().build();
    }

    // Crear una factura desde una orden COMPLETADA
    @PostMapping("/generar-desde-orden/{orderId}")
    public ResponseEntity<Invoice> generarDesdeOrden(@PathVariable Integer orderId) {
        Invoice factura = invoiceService.generarFacturaDesdeOrden(orderId);
        return ResponseEntity.ok(factura);
    }

}
