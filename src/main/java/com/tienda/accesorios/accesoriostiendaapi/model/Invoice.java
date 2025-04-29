package com.tienda.accesorios.accesoriostiendaapi.model;

import com.tienda.accesorios.accesoriostiendaapi.dto.InvoiceResponse;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "text")
    private String enterprisedata;

    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double subtotal;
    private Double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterprisedata() {
        return enterprisedata;
    }

    public void setEnterprisedata(String enterprisedata) {
        this.enterprisedata = enterprisedata;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public InvoiceResponse mapToResponse(Invoice factura) {
        return InvoiceResponse.builder()
                .id(factura.getId())
                .numero(factura.getNumero())
                .fecha(factura.getFecha())
                .clienteNombre(factura.getCliente().getNombre()) // o getNombreCompleto()
                .total(factura.getTotal())
                .metodoPago(factura.getMetodoPago().toString()) // Enum o String
                .estado(factura.getEstado().toString()) // Enum o String
                .vencimiento(factura.getFechaVencimiento())
                .build();
    }

}
