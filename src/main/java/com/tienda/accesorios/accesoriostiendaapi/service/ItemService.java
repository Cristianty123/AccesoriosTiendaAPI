package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.exception.ItemNotFoundException;
import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemRepository;
import com.tienda.accesorios.accesoriostiendaapi.exception.NoItemsFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public ItemPageResponse getItemsByPage(int pageNumber, Optional<String> itemTypeId) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("createdAt").descending());

        Page<Item> itemsPage = itemTypeId
                .map(id -> itemRepository.findAllByItemtypeIdOrderByCreatedAtDesc(id, pageable))
                .orElseGet(() -> itemRepository.findAllByOrderByCreatedAtDesc(pageable));


        if (itemsPage.isEmpty()) {
            throw new NoItemsFoundException("La página seleccionada no tiene items.");
        }

        List<ItemResponse> items = itemsPage.stream()
                .map(item -> new ItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getDescription(),
                        item.getStock(),
                        item.getSellingprice(),
                        item.getPurchaseprice(),
                        item.getItemstate(),
                        item.getItemtype(),
                        item.getimageUrl()))
                .toList();

        return new ItemPageResponse(itemsPage.getTotalPages(), pageNumber, items);
    }
    public ItemResponse getItemById(String itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("El item con ID " + itemId + " no fue encontrado."));

        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getStock(),
                item.getSellingprice(),
                item.getPurchaseprice(),
                item.getItemstate(),
                item.getItemtype(),
                item.getimageUrl()
        );
    }

}
