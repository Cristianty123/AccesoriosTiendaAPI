package com.tienda.accesorios.accesoriostiendaapi.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Item_AdditionalExpense")
public class ItemAdditionalExpense implements Serializable {

    @EmbeddedId
    private ItemAdditionalExpenseId id;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @MapsId("expenseId")
    @JoinColumn(name = "expense_id")
    private AdditionalExpense additionalExpense;

    public ItemAdditionalExpense() {}

    public ItemAdditionalExpense(Item item, AdditionalExpense additionalExpense) {
        this.item = item;
        this.additionalExpense = additionalExpense;
        this.id = new ItemAdditionalExpenseId(item.getId(), additionalExpense.getId());
    }

    // Getters y setters

    public ItemAdditionalExpenseId getId() {
        return id;
    }

    public void setId(ItemAdditionalExpenseId id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public AdditionalExpense getAdditionalExpense() {
        return additionalExpense;
    }

    public void setAdditionalExpense(AdditionalExpense additionalExpense) {
        this.additionalExpense = additionalExpense;
    }
}
