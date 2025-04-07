package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.accesorios.accesoriostiendaapi.dto.AdditionalExpenseResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemAdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.service.ImageService;
import com.tienda.accesorios.accesoriostiendaapi.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemAdditionalExpenseRepository itemAdditionalExpenseRepository;
    private final AdditionalExpenseRepository additionalExpenseRepository;
    private final ImageService imageService;
    private final ObjectMapper objectMapper;
    private final ItemService itemService;

    public ItemController(ItemRepository itemRepository, ItemAdditionalExpenseRepository itemAdditionalExpenseRepository, AdditionalExpenseRepository additionalExpenseRepository, ItemService itemService, ObjectMapper objectMapper, ImageService imageService) {
        this.itemRepository = itemRepository;
        this.itemAdditionalExpenseRepository = itemAdditionalExpenseRepository;
        this.additionalExpenseRepository = additionalExpenseRepository;
        this.itemService = itemService;
        this.objectMapper = objectMapper;
        this.imageService = imageService;
    }

    // Guardar un nuevo Item desde un JSON
    @PostMapping(value = "/add")
    public ItemResponse agregarItem(
            @RequestPart("item") String itemJson,
            @RequestPart("image") MultipartFile imageFile) throws IOException {

        ItemRequest itemRequest = objectMapper.readValue(itemJson, ItemRequest.class);

        String imageurl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageurl = imageService.saveImage(imageFile);
        }

        // Crear objeto Item
        Item item = new Item(
                itemRequest.getId(),
                itemRequest.getName(),
                itemRequest.getDescription(),
                itemRequest.getStock(),
                itemRequest.getSellingprice(),
                itemRequest.getPurchaseprice(),
                itemRequest.getItemstate(),
                imageurl,
                itemRequest.getItemtype()
        );

        Item savedItem = itemRepository.save(item);

        // Asociar gastos adicionales si los hay
        if (itemRequest.getAdditionalExpenseIds() != null) {
            for (Integer expenseId : itemRequest.getAdditionalExpenseIds()) {
                Optional<AdditionalExpense> expenseOpt = additionalExpenseRepository.findById(expenseId);
                if (expenseOpt.isPresent()) {
                    AdditionalExpense expense = expenseOpt.get();
                    ItemAdditionalExpense relation = new ItemAdditionalExpense(savedItem, expense);
                    itemAdditionalExpenseRepository.save(relation);
                }
            }
        }

        return new ItemResponse(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getStock(),
                savedItem.getSellingprice(),
                savedItem.getPurchaseprice(),
                savedItem.getItemstate(),
                savedItem.getItemtype(),
                savedItem.getimageUrl()
        );
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
    @GetMapping("/page")
    public ResponseEntity<ItemPageResponse> getItemsByPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String itemTypeId) {

        ItemPageResponse response = itemService.getItemsByPage(page, Optional.ofNullable(itemTypeId));
        return ResponseEntity.ok(response);
    }
}
