package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.dto.AdditionalExpenseDTO;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.model.AdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemAdditionalExpenseRepository;
import org.springframework.web.bind.annotation.*;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemAdditionalExpenseRepository itemAdditionalExpenseRepository;

    @Autowired
    private AdditionalExpenseRepository additionalExpenseRepository;

    // Guardar un nuevo Item desde un JSON
    @PostMapping("/add")
    public Item agregarItem(@RequestBody ItemRequest itemRequest) {
        // Convertir Base64 a byte[]
        byte[] imageBytes = Base64.getDecoder().decode(itemRequest.getImage());

        // Crear objeto Item
        Item item = new Item(
                itemRequest.getId(),
                itemRequest.getName(),
                itemRequest.getDescription(),
                itemRequest.getStock(),
                itemRequest.getSellingPrice(),
                itemRequest.getPurchasePrice(),
                itemRequest.getItemState(),
                imageBytes,
                itemRequest.getItemType()
        );

        Item savedItem = itemRepository.save(item);

        for (Integer expenseId : itemRequest.getAdditionalExpenseIds()) {
            Optional<AdditionalExpense> expenseOpt = additionalExpenseRepository.findById(expenseId);
            if (expenseOpt.isPresent()) {
                AdditionalExpense expense = expenseOpt.get();
                ItemAdditionalExpense relation = new ItemAdditionalExpense(savedItem, expense);
                itemAdditionalExpenseRepository.save(relation);
            }
        }

        return savedItem;
    }

    // Obtener un item
    @GetMapping("/{id}")
    public ItemResponse getItem(@PathVariable String id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            String pictureBase64 = Base64.getEncoder().encodeToString(item.getImage());

            return new ItemResponse(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getStock(),
                    item.getSellingPrice(),
                    item.getPurchasePrice(),
                    item.getItemState(),
                    item.getItemType(),
                    pictureBase64
            );
        } else {
            throw new RuntimeException("Item no encontrado");
        }
    }
    @GetMapping("/{id}/gastos-adicionales")
    public List<AdditionalExpenseDTO> obtenerGastosPorItem(@PathVariable Integer id) {
        List<ItemAdditionalExpense> relaciones = itemAdditionalExpenseRepository.findByItemId(id);

        return relaciones.stream()
                .map(rel -> {
                    AdditionalExpense gasto = rel.getAdditionalExpense();
                    return new AdditionalExpenseDTO(
                            gasto.getId(),
                            gasto.getName(),
                            gasto.getExpense(),
                            gasto.getDescription()
                    );
                })
                .toList();
    }
}
