package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.dto.AdditionalExpenseResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.AditionalExpenseRequest;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.AdditionalExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping("/getall")
    public ResponseEntity<List<AdditionalExpenseResponse>> obtenerTodos() {
        List<AdditionalExpenseResponse> lista = service.getAllAdditionalExpenses();
        return ResponseEntity.ok(lista);
    }
}
