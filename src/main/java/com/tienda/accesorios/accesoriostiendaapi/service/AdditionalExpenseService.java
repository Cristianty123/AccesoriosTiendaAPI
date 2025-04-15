package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.AdditionalExpenseResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalExpenseService {

    @Autowired
    private AdditionalExpenseRepository repository;

    public boolean existsByNameAndExpense(String name, double expense) {
        return repository.findByNameAndExpense(name, expense).isPresent();
    }
    public List<AdditionalExpenseResponse> getAllAdditionalExpenses() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AdditionalExpenseResponse mapToDto(AdditionalExpense entity) {
        return new AdditionalExpenseResponse(
                entity.getId(),
                entity.getName(),
                entity.getExpense(),
                entity.getDescription()
        );
    }
}
