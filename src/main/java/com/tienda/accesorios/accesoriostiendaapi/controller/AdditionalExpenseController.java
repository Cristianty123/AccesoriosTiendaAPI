package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.dto.AditionalExpenseRequest;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.AdditionalExpenseService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/additionalExpenses")
public class AdditionalExpenseController {

    @Autowired
    private AdditionalExpenseRepository additionalExpenseRepository;

    @Autowired
    private AdditionalExpenseService service;

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> crearGastoAdicional(@RequestBody AditionalExpenseRequest dto) {
        // Verifica si ya existe por nombre y valor
        if (service.existsByNameAndExpense(dto.getName(), dto.getExpense())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El gasto adicional ya existe.");
        }

        AdditionalExpense nuevo = new AdditionalExpense();
        nuevo.setName(dto.getName());
        nuevo.setExpense(dto.getExpense());
        nuevo.setDescription(dto.getDescription());

        additionalExpenseRepository.save(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body("Gasto adicional creado correctamente.");
    }
}
