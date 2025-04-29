package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseOrderTaxId implements Serializable {

    private Integer orderId;
    private Integer taxId;

    public PurchaseOrderTaxId() {}

    public PurchaseOrderTaxId(Integer orderId, Integer taxId) {
        this.orderId = orderId;
        this.taxId = taxId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
        if (!(o instanceof PurchaseOrderTaxId that)) return false;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(taxId, that.taxId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, taxId);
    }
}
