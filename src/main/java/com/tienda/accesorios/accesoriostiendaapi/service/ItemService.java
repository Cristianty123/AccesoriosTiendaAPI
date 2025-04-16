package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.exception.ItemNotFoundException;
import com.tienda.accesorios.accesoriostiendaapi.model.Discount;
import com.tienda.accesorios.accesoriostiendaapi.model.Item;
import com.tienda.accesorios.accesoriostiendaapi.model.ItemAdditionalExpense;
import com.tienda.accesorios.accesoriostiendaapi.repository.AdditionalExpenseRepository;
import com.tienda.accesorios.accesoriostiendaapi.repository.DiscountRepository;
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
    private final DiscountRepository discountRepository;


    public ItemService(
            ItemRepository itemRepository,
            AdditionalExpenseRepository additionalExpenseRepository,
            ItemAdditionalExpenseRepository itemAdditionalExpenseRepository,
            ImageService imageService,
            EntityManager entityManager,
            DiscountRepository discountRepository // <- este
    ) {
        this.itemRepository = itemRepository;
        this.additionalExpenseRepository = additionalExpenseRepository;
        this.itemAdditionalExpenseRepository = itemAdditionalExpenseRepository;
        this.imageService = imageService;
        this.entityManager = entityManager;
        this.discountRepository = discountRepository; // <- y este
    }

    public ItemResponse createItem(ItemRequest itemRequest, MultipartFile imageFile) throws IOException {
        String imageurl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageurl = imageService.saveImage(imageFile);
        }

        Long siguienteValor = ((Number) entityManager
                .createNativeQuery("SELECT nextval('item_seq')")
                .getSingleResult()).longValue();
        String idBonito = "ITEM-" + siguienteValor;

        Item item = new Item(
                idBonito,
                itemRequest.getName(),
                itemRequest.getStock(),
                itemRequest.getDescription(),
                itemRequest.getPurchaseprice(),
                itemRequest.getSellingprice(),
                itemRequest.getItemstate(),
                imageurl,
                itemRequest.getItemtype(),
                itemRequest.isFree_shipping(),
                itemRequest.getPrice_shipping()
        );

        // Lógica para descuento
        if(itemRequest.getDiscountId() != null) {
            // Se busca el descuento en el repositorio
            Optional<Discount> discountOptional = discountRepository.findById(itemRequest.getDiscountId());
            if(discountOptional.isPresent()) {
                // Asumimos que el campo discount en Item es opcional y representa el id del descuento
                // o podrías tener una relación de entidad si el modelo lo permite.
                item.setDiscount(discountOptional.get());
                // También podrías considerar asignar la entidad Discount,
                // dependiendo de cómo esté mapeada la relación (por ejemplo, @ManyToOne)
            } else {
                // Manejar el caso en el que el id de descuento enviado no existe.
                throw new IllegalArgumentException("El descuento con id " + itemRequest.getDiscountId() + " no existe.");
            }
        } else {
            // Si no se envió descuento, se puede dejar como null o asignar un valor por defecto.
            item.setDiscount(null);
        }

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
                savedItem.getItemtype(),
                savedItem.isFree_shipping(),
                savedItem.getPrice_shipping(),
                savedItem.getimageurl()
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
                        item.getItemtype(),
                        item.isFree_shipping(),
                        item.getPrice_shipping(),
                        item.getimageurl()))
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
                item.getItemtype(),
                item.isFree_shipping(),
                item.getPrice_shipping(),
                item.getimageurl()
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
