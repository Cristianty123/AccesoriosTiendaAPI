package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.AdditionalExpenseResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemAdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemAdditionalExpenseRepository itemAdditionalExpenseRepository;
    private final ObjectMapper objectMapper;
    private final ItemService itemService;

    public ItemController(ItemAdditionalExpenseRepository itemAdditionalExpenseRepository, ItemService itemService, ObjectMapper objectMapper) {
        this.itemAdditionalExpenseRepository = itemAdditionalExpenseRepository;
        this.itemService = itemService;
        this.objectMapper = objectMapper;
    }

    // Guardar un nuevo Item desde un JSON
    @PostMapping(value = "/add")
    public ResponseEntity<ItemResponse> agregarItem(
            @RequestPart("item") String itemJson,
            @RequestPart("image") MultipartFile imageFile) throws IOException {

        ItemRequest itemRequest = objectMapper.readValue(itemJson, ItemRequest.class);
        ItemResponse response = itemService.createItem(itemRequest, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // Obtener un item
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable String id) {
        ItemResponse item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }
    @GetMapping("/{id}/gastos-adicionales")
    public List<AdditionalExpenseResponse> obtenerGastosPorItem(@PathVariable String id) {
        List<ItemAdditionalExpense> relaciones = itemAdditionalExpenseRepository.findByItemId(id);

        return relaciones.stream()
                .map(rel -> {
                    AdditionalExpense gasto = rel.getAdditionalExpense();
                    return new AdditionalExpenseResponse(
                            gasto.getId(),
                            gasto.getName(),
                            gasto.getExpense(),
                            gasto.getDescription()
                    );
                })
                .toList();
    }
    @GetMapping("/public/page")
    public ResponseEntity<ItemPageResponse> getItemsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String itemTypeId) {

        ItemPageResponse response = itemService.getItemsByPage(page, Optional.ofNullable(itemTypeId));
        return ResponseEntity.ok(response);
    }
}
