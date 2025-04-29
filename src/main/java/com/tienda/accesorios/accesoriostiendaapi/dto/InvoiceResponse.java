package com.tienda.accesorios.accesoriostiendaapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceResponse {

    private Integer id;
    private LocalDateTime fecha;
    private String clienteNombre;
    private Double total;
    private String numero;
    private String metodoPago; // Enum o String
    private String estado;
    private LocalDate vencimiento;

    // Getters y Setters (ya los tienes, pero aquí están para completar la clase)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    // Método estático para iniciar el builder
    public static InvoiceResponseBuilder builder() {
        return new InvoiceResponseBuilder();
    }

    // Clase Builder interna
    public static class InvoiceResponseBuilder {
        private Integer id;
        private LocalDateTime fecha;
        private String clienteNombre;
        private Double total;
        private String numero;
        private String metodoPago;
        private String estado;
        private LocalDate vencimiento;

        public InvoiceResponseBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public InvoiceResponseBuilder fecha(LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }

        public InvoiceResponseBuilder clienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
            return this;
        }

        public InvoiceResponseBuilder total(Double total) {
            this.total = total;
            return this;
        }

        public InvoiceResponseBuilder numero(String numero) {
            this.numero = numero;
            return this;
        }

        public InvoiceResponseBuilder metodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }

        public InvoiceResponseBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public InvoiceResponseBuilder vencimiento(LocalDate vencimiento) {
            this.vencimiento = vencimiento;
            return this;
        }

        public InvoiceResponse build() {
            InvoiceResponse dto = new InvoiceResponse();
            dto.setId(id);
            dto.setFecha(fecha);
            dto.setClienteNombre(clienteNombre);
            dto.setTotal(total);
            dto.setNumero(numero);
            dto.setMetodoPago(metodoPago);
            dto.setEstado(estado);
            dto.setVencimiento(vencimiento);
            return dto;
        }
    }
}

