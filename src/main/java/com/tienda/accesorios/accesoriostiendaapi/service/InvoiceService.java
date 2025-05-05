package com.tienda.accesorios.accesoriostiendaapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.InvoicePageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.InvoiceResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.exception.NoInvoicesFoundException;
import com.tienda.accesorios.accesoriostiendaapi.model.Invoice;
import com.tienda.accesorios.accesoriostiendaapi.model.PurchaseOrder;
import com.tienda.accesorios.accesoriostiendaapi.repository.InvoiceRepository;

import com.tienda.accesorios.accesoriostiendaapi.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, PurchaseOrderRepository purchaseOrderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public Invoice generarFacturaDesdeOrden(Integer orderId) {
        PurchaseOrder orden = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (!orden.getEstado().getName().equalsIgnoreCase("COMPLETADA")) {
            throw new IllegalStateException("Solo se puede generar una factura de una orden completada.");
        }

        if (orden.getInvoice() != null) {
            throw new IllegalStateException("Ya existe una factura para esta orden.");
        }

        Invoice factura = new Invoice();
        factura.setOrder(orden);
        factura.setDatetime(LocalDateTime.now());
        factura.setCustomer(orden.getCustomer());
        factura.setSubtotal(orden.getSubtotal());
        factura.setTotal(orden.getTotal());

        // Puedes almacenar info del cliente en enterpriseData si usas JSON
        factura.setEnterprisedata("Información relevante del cliente...");

        return invoiceRepository.save(factura);
    }

    public List<InvoiceResponse> obtenerFacturas(String estado, Long clienteId,
                                                 LocalDate fechaDesde, LocalDate fechaHasta,
                                                 String buscar, int pagina, int tamaño) {
        List<Invoice> facturas = invoiceRepository.findAll();

        // Filtro por cliente
        if (clienteId != null) {
            facturas = facturas.stream()
                    .filter(f -> f.getCustomer().getId().equals(clienteId.intValue()))
                    .collect(Collectors.toList());
        }

        // Filtro por fecha
        if (fechaDesde != null) {
            facturas = facturas.stream()
                    .filter(f -> f.getDatetime().toLocalDate().compareTo(fechaDesde) >= 0)
                    .collect(Collectors.toList());
        }

        if (fechaHasta != null) {
            facturas = facturas.stream()
                    .filter(f -> f.getDatetime().toLocalDate().compareTo(fechaHasta) <= 0)
                    .collect(Collectors.toList());
        }

        // Filtro por término de búsqueda (ej. número de factura en enterprisedata)
        if (buscar != null && !buscar.isBlank()) {
            facturas = facturas.stream()
                    .filter(f -> f.getEnterprisedata().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Paginación manual
        int fromIndex = pagina * tamaño;
        int toIndex = Math.min(fromIndex + tamaño, facturas.size());

        if (fromIndex > facturas.size()) {
            return List.of(); // Página vacía
        }

        List<Invoice> paginadas = facturas.subList(fromIndex, toIndex);

        return paginadas.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private InvoiceResponse mapToResponse(Invoice factura) {
        ObjectMapper mapper = new ObjectMapper();

        String numero = null;
        String metodoPago = null;
        String estado = null;
        LocalDate vencimiento = null;

        try {
            // Parsear enterpriseData si tiene estructura JSON
            JsonNode node = mapper.readTree(factura.getEnterprisedata());

            numero = node.has("numero") ? node.get("numero").asText() : null;
            metodoPago = node.has("metodoPago") ? node.get("metodoPago").asText() : null;
            estado = node.has("estado") ? node.get("estado").asText() : null;
            vencimiento = node.has("vencimiento") ? LocalDate.parse(node.get("vencimiento").asText()) : null;

        } catch (Exception e) {
            // Manejo de error si el JSON está mal formado
            System.err.println("Error al parsear enterprisedata de factura ID " + factura.getId());
        }

        return InvoiceResponse.builder()
                .id(factura.getId())
                .fecha(factura.getDatetime())
                .clienteNombre(factura.getCustomer().getCustomername())
                .total(factura.getTotal())
                .numero(numero)
                .metodoPago(metodoPago)
                .estado(estado)
                .vencimiento(vencimiento)
                .build();
    }

    public InvoiceResponse obtenerFacturaPorId(Integer id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return mapToResponse(factura);
    }

    public byte[] generarPdf(Integer id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        String contenido = "Factura PDF\n" +
                "N°: " + factura.getId() + "\n" +
                "Cliente: " + factura.getCustomer().getCustomername() + "\n" +
                "Fecha: " + factura.getDatetime() + "\n" +
                "Total: $" + factura.getTotal();

        // En una app real usarías JasperReports, iText, etc.
        return contenido.getBytes();
    }

    public byte[] obtenerDatosImprimibles(Integer id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        String datos = "FACTURA\n" +
                "----------------------\n" +
                "ID: " + factura.getId() + "\n" +
                "Cliente: " + factura.getCustomer().getCustomername() + "\n" +
                "Fecha: " + factura.getDatetime() + "\n" +
                "Subtotal: $" + factura.getSubtotal() + "\n" +
                "Total: $" + factura.getTotal() + "\n";

        return datos.getBytes();
    }

    public void enviarRecordatorio(Integer id) {
        Invoice factura = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Simular envío de recordatorio
        System.out.println("Enviando recordatorio al cliente: " +
                factura.getCustomer().getCustomername() +
                " - Factura ID: " + factura.getId());
    }

    public InvoicePageResponse getInvoicesByPage(
            int pageNumber,
            Optional<String> search,
            Optional<String> status,
            Optional<Long> customerId,
            Optional<LocalDate> dateFrom,
            Optional<LocalDate> dateTo) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("createdAt").descending());

        Specification<Invoice> spec = Specification.where(null);

        // Búsqueda por número o nombre de cliente
        if (search.isPresent() && !search.get().isBlank()) {
            String searchTerm = "%" + search.get().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("invoiceNumber").as(String.class)), searchTerm),
                    cb.like(cb.lower(root.get("customer").get("name")), searchTerm)
            ));
        }

        // Estado
        if (status.isPresent() && !status.get().equalsIgnoreCase("all")) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status.get())
            );
        }

        // Cliente
        if (customerId.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("customer").get("id"), customerId.get())
            );
        }

        // Fecha desde
        if (dateFrom.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("createdAt").as(LocalDate.class), dateFrom.get())
            );
        }

        // Fecha hasta
        if (dateTo.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("createdAt").as(LocalDate.class), dateTo.get())
            );
        }

        Page<Invoice> invoicePage = invoiceRepository.findAll(spec, pageable);

        if (invoicePage.isEmpty()) {
            throw new NoInvoicesFoundException("No se encontraron facturas para la página seleccionada.");
        }

        List<InvoiceResponse> invoices = invoicePage.stream()
                .map(invoice -> {
                    // Manejo de campos que podrían ser null
                    String clienteNombre = invoice.getCustomer() != null
                            ? invoice.getCustomer().getCustomername()
                            : "Cliente no disponible";
                    String estado = invoice.getOrder() != null
                            ? invoice.getOrder().getEstado().toString()
                            : "Estado no disponible";

                    return InvoiceResponse.builder()
                            .id(invoice.getId())
                            .fecha(invoice.getDatetime())
                            .clienteNombre(clienteNombre)
                            .total(invoice.getTotal())
                            .estado(estado)
                            .build();
                })
                .toList();

        List<String> pagesToShow = calculatePagesToShow(invoicePage.getTotalPages(), pageNumber);

        return new InvoicePageResponse(invoicePage.getTotalPages(), pageNumber, pagesToShow, invoices);
    }

    private List<String> calculatePagesToShow(int totalPages, int currentPage) {
        List<String> pages = new ArrayList<>();

        if (totalPages <= 13) {
            for (int i = 1; i <= totalPages; i++) {
                pages.add(String.valueOf(i));
            }
            return pages;
        }

        pages.add("1");
        pages.add("2");

        if (currentPage > 5) {
            pages.add("...");
        }

        int start = Math.max(3, currentPage - 3);
        int end = Math.min(totalPages - 2, currentPage + 3);

        for (int i = start; i <= end; i++) {
            pages.add(String.valueOf(i));
        }

        if (currentPage < totalPages - 4) {
            pages.add("...");
        }

        pages.add(String.valueOf(totalPages - 1));
        pages.add(String.valueOf(totalPages));

        return pages;
    }
}
