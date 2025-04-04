package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemAdditionalExpenseId implements Serializable {

    private String itemId;
    private Integer expenseId;

    public ItemAdditionalExpenseId() {}

    public ItemAdditionalExpenseId(String itemId, Integer expenseId) {
        this.itemId = itemId;
        this.expenseId = expenseId;
    }

    // Getters, setters, equals y hashCode

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemAdditionalExpenseId)) return false;
        ItemAdditionalExpenseId that = (ItemAdditionalExpenseId) o;
        return Objects.equals(itemId, that.itemId) &&
                Objects.equals(expenseId, that.expenseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, expenseId);
    }
}
