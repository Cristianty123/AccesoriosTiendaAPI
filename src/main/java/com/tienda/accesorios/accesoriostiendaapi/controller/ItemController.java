package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.*;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemAdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    /**
     *
     * Endpotin que se encarga de agreagr Productos
     *
     */
    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<ItemResponse> agregarItem(
            @RequestPart("item") String itemJson,
            @RequestPart("image") MultipartFile imageFile,
            Authentication authentication) throws IOException {

        String username = authentication.getName();
        ItemRequest itemRequest = objectMapper.readValue(itemJson, ItemRequest.class);
        ItemResponse response = itemService.createItem(itemRequest, imageFile, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     *
     * Endpotin que se encarga de modificar el stock
     *
     */
    @PutMapping("/{id}/stock")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<ItemResponse> updateItemStock(
            @PathVariable String id,
            @RequestBody StockUpdateRequest stockUpdateRequest,
            Authentication authentication) {

        String username = authentication.getName();
        ItemResponse response = itemService.updateItemStock(id, stockUpdateRequest, username);
        return ResponseEntity.ok(response);
    }

    /**
     *
     * Endpotin que se encarga de eliminar un item
     *
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<Void> deleteItem(
            @PathVariable String id,
            Authentication authentication) {

        String username = authentication.getName();
        itemService.deleteItem(id, username);
        return ResponseEntity.noContent().build();
    }

    /**
     *
     * Endpotin que se encarga de actualizar un Productos
     *
     */
    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<ItemResponse> updateItem(
            @PathVariable String id,
            @RequestPart("item") String itemJson,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            Authentication authentication) throws IOException {

        String username = authentication.getName();
        ItemRequest itemRequest = objectMapper.readValue(itemJson, ItemRequest.class);
        ItemResponse response = itemService.updateItem(id, itemRequest, imageFile, username);
        return ResponseEntity.ok(response);
    }

    /**
     *
     * Endpotin que se encarga de obtener un Productos por Id
     *
     */
    @GetMapping("/public/{id}")
    public ResponseEntity<ItemResponse> getItem(@PathVariable String id) {
        ItemResponse item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    /**
     *
     * Endpotin que se encarga de obtener gastos adicionales de cada producto
     *
     */
    @GetMapping("/public/{id}/gastos-adicionales")
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

    /**
     *
     * Endpotin que se encarga de obtener Productos por pagina
     *
     */
    @GetMapping("/public/page")
    public ResponseEntity<ItemPageResponse> getItemsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String itemTypeId) {

        ItemPageResponse response = itemService.getItemsByPage(page, Optional.ofNullable(itemTypeId));
        return ResponseEntity.ok(response);
    }
}
