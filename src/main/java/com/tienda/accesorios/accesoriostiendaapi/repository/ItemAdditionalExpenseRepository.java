package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpenseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemAdditionalExpenseRepository extends JpaRepository<ItemAdditionalExpense, ItemAdditionalExpenseId>  {
    List<ItemAdditionalExpense> findByItemId(Integer itemId);
}
