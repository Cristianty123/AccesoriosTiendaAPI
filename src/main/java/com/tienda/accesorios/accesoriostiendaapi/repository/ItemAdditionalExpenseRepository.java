package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpenseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemAdditionalExpenseRepository extends JpaRepository<ItemAdditionalExpense, ItemAdditionalExpenseId>  {
    List<ItemAdditionalExpense> findByItemId(String itemId);

    @Modifying
    @Query("DELETE FROM ItemAdditionalExpense iae WHERE iae.item.id = :itemId")
    void deleteByItemId(@Param("itemId") String itemId);
}
