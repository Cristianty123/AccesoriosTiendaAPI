package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseOrderDiscountId implements Serializable {

    private Integer orderId;
    private Integer discountId;

    public PurchaseOrderDiscountId() {}

    public PurchaseOrderDiscountId(Integer orderId, Integer discountId) {
        this.orderId = orderId;
        this.discountId = discountId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseOrderDiscountId that)) return false;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(discountId, that.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, discountId);
    }
}
