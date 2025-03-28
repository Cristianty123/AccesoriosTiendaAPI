package com.tienda.accesorios.accesoriostiendaapi.controller;

import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<Item> agregarItem(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("sellingPrice") Double sellingPrice,
            @RequestParam("purchasePrice") Double purchasePrice,
            @RequestParam("itemState") Boolean itemState,
            @RequestParam("image") MultipartFile file) throws IOException {

        // Guardar la imagen en el sistema de archivos
        String imagePath = itemService.guardarImagen(file);

        // Crear el objeto Item con la URL de la imagen
        Item item = new Item(id, name, description, stock, sellingPrice, purchasePrice, itemState, imagePath);

        return ResponseEntity.ok(itemService.agregarItem(item));
    }
}
