package com.tienda.accesorios.accesoriostiendaapi.repository;

import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalExpenseRepository extends JpaRepository<AdditionalExpense, Integer> {

}

