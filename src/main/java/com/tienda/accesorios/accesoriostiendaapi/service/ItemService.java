package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.exception.ItemNotFoundException;
import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemAdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.repository.ItemRepository;
import com.tienda.accesorios.accesoriostiendaapi.exception.NoItemsFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AdditionalExpenseRepository additionalExpenseRepository;
    private final ItemAdditionalExpenseRepository itemAdditionalExpenseRepository;
    private final ImageService imageService;
    private final EntityManager entityManager;

    public ItemService(ItemRepository itemRepository, AdditionalExpenseRepository additionalExpenseRepository, ItemAdditionalExpenseRepository itemAdditionalExpenseRepository, ImageService imageService, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.additionalExpenseRepository = additionalExpenseRepository;
        this.itemAdditionalExpenseRepository = itemAdditionalExpenseRepository;
        this.imageService = imageService;
        this.entityManager = entityManager;
    }

    public ItemResponse createItem(ItemRequest itemRequest, MultipartFile imageFile) throws IOException {
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = imageService.saveImage(imageFile);
        }

        Long siguienteValor = ((Number) entityManager
                .createNativeQuery("SELECT nextval('item_seq')")
                .getSingleResult()).longValue();
        String idBonito = "ITEM-" + siguienteValor;

        Item item = new Item(
                idBonito,
                itemRequest.getName(),
                itemRequest.getDescription(),
                itemRequest.getStock(),
                itemRequest.getSellingprice(),
                itemRequest.getPurchaseprice(),
                itemRequest.getItemstate(),
                imageUrl,
                itemRequest.getItemtype()
        );

        Item savedItem = itemRepository.save(item);

        if (itemRequest.getAdditionalExpenseIds() != null) {
            for (Integer expenseId : itemRequest.getAdditionalExpenseIds()) {
                additionalExpenseRepository.findById(expenseId).ifPresent(expense -> {
                    itemAdditionalExpenseRepository.save(new ItemAdditionalExpense(savedItem, expense));
                });
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

        List<String> pagesToShow = calculatePagesToShow(itemsPage.getTotalPages(), pageNumber);

        return new ItemPageResponse(itemsPage.getTotalPages(), pageNumber, pagesToShow, items);
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
    private List<String> calculatePagesToShow(int totalPages, int currentPage) {
        List<String> pages = new ArrayList<>();

        if (totalPages <= 13) {
            for (int i = 1; i <= totalPages; i++) {
                pages.add(String.valueOf(i));
            }
            return pages;
        }

        pages.add("1");
        pages.add("2");

        if (currentPage > 5) {
            pages.add("...");
        }

        int start = Math.max(3, currentPage - 3);
        int end = Math.min(totalPages - 2, currentPage + 3);

        for (int i = start; i <= end; i++) {
            pages.add(String.valueOf(i));
        }

        if (currentPage < totalPages - 4) {
            pages.add("...");
        }

        pages.add(String.valueOf(totalPages - 1));
        pages.add(String.valueOf(totalPages));

        return pages;
    }


}
