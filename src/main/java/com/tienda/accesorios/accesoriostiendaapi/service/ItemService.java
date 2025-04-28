package com.tienda.accesorios.accesoriostiendaapi.service;

import com.tienda.accesorios.accesoriostiendaapi.dto.ItemPageResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemRequest;
import com.tienda.accesorios.accesoriostiendaapi.dto.ItemResponse;
import com.tienda.accesorios.accesoriostiendaapi.dto.StockUpdateRequest;
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
import org.springframework.transaction.annotation.Transactional;
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

    public ItemResponse createItem(ItemRequest itemRequest, MultipartFile imageFile,String username) throws IOException {
        String imageurl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageurl = imageService.saveImage(imageFile);
        }

        Long siguienteValor = ((Number) entityManager
                .createNativeQuery("SELECT nextval('item_seq')")
                .getSingleResult()).longValue();
        String idBonito = "ITEM-" + siguienteValor;

        System.out.println("Creando item por usuario: " + username);

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
        return convertToItemResponse(savedItem);
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

        return convertToItemResponse(item);
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
    public ItemResponse updateItemStock(String itemId, StockUpdateRequest request, String username) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item no encontrado con ID: " + itemId));

        switch (request.getAction()) {
            case "add":
                item.setStock(item.getStock() + request.getAmount());
                break;
            case "remove":
                item.setStock(Math.max(0, item.getStock() - request.getAmount()));
                break;
            case "set":
                item.setStock(request.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Acción de stock no válida: " + request.getAction());
        }

        // Aquí podrías registrar el cambio de stock en un historial con el username y motivo
        System.out.println("Stock actualizado por " + username + ". Motivo: " + request.getReason());

        Item updatedItem = itemRepository.save(item);
        return convertToItemResponse(updatedItem);
    }

    @Transactional
    public ItemResponse updateItem(String itemId, ItemRequest itemRequest, MultipartFile imageFile, String username) throws IOException {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item no encontrado con ID: " + itemId));


        // Guardar la URL de la imagen anterior antes de actualizar
        String oldImageUrl = existingItem.getImageurl();

        // Actualizar campos básicos
        existingItem.setName(itemRequest.getName());
        existingItem.setDescription(itemRequest.getDescription());
        existingItem.setStock(itemRequest.getStock());
        existingItem.setSellingprice(itemRequest.getSellingprice());
        existingItem.setPurchaseprice(itemRequest.getPurchaseprice());
        existingItem.setItemstate(itemRequest.getItemstate());
        existingItem.setItemtype(itemRequest.getItemtype());
        existingItem.setFree_shipping(itemRequest.isFree_shipping());
        existingItem.setPrice_shipping(itemRequest.getPrice_shipping());

        // Actualizar imagen si se proporciona
        if (imageFile != null && !imageFile.isEmpty()) {
            // Eliminar la imagen anterior si existe
            if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
                try {
                    imageService.deleteImage(oldImageUrl);
                } catch (IOException e) {
                    System.err.println("Error al eliminar la imagen anterior: " + e.getMessage());
                    // No lanzamos la excepción para no interrumpir el proceso de actualización
                }
            }
            // Guardar la nueva imagen
            String imageUrl = imageService.saveImage(imageFile);
            existingItem.setImageurl(imageUrl);
        }

        // Actualizar descuento
        if (itemRequest.getDiscountId() != null) {
            Discount discount = discountRepository.findById(itemRequest.getDiscountId())
                    .orElseThrow(() -> new IllegalArgumentException("Descuento no encontrado"));
            existingItem.setDiscount(discount);
        } else {
            existingItem.setDiscount(null);
        }

        // Actualizar gastos adicionales
        itemAdditionalExpenseRepository.deleteByItemId(itemId);
        if (itemRequest.getAdditionalExpenseIds() != null) {
            for (Integer expenseId : itemRequest.getAdditionalExpenseIds()) {
                additionalExpenseRepository.findById(expenseId).ifPresent(expense -> {
                    itemAdditionalExpenseRepository.save(new ItemAdditionalExpense(existingItem, expense));
                });
            }
        }

        Item updatedItem = itemRepository.save(existingItem);
        System.out.println("Item actualizado por usuario: " + username);
        return convertToItemResponse(updatedItem);
    }
    @Transactional
    public void deleteItem(String itemId, String username) {
        // Primero obtenemos el item para ver si tiene imagen asociada
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item no encontrado con ID: " + itemId));

        // Eliminar la imagen del servidor si existe
        if (item.getImageurl() != null && !item.getImageurl().isEmpty()) {
            try {
                imageService.deleteImage(item.getImageurl());
                System.out.println("Imagen del item eliminada: " + item.getImageurl());
            } catch (IOException e) {
                System.err.println("Error al eliminar la imagen del item: " + e.getMessage());
                // Continuamos con la eliminación aunque falle el borrado de la imagen
            }
        }

        // Eliminar relaciones con gastos adicionales
        itemAdditionalExpenseRepository.deleteByItemId(itemId);

        // Finalmente eliminar el item
        itemRepository.deleteById(itemId);
        System.out.println("Item eliminado por usuario: " + username);
    }

    private ItemResponse convertToItemResponse(Item item) {
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
                item.getImageurl()
        );
    }
}
