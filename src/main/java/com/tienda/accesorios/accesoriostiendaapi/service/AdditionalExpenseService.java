package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdditionalExpenseService {

    @Autowired
    private AdditionalExpenseRepository repository;

    public boolean existsByNameAndExpense(String name, double expense) {
        return repository.findByNameAndExpense(name, expense).isPresent();
    }

    public void save(AdditionalExpense expense) {
        repository.saveAndFlush(expense);
    }
}
