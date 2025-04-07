package com.tienda.accesorios.accesoriostiendaapi.dto;

import java.util.List;

public class ItemPageResponse {
    private int totalPages;
    private int currentPage;
    private List<ItemResponse> items;

    public ItemPageResponse(int totalPages, int currentPage, List<ItemResponse> items) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.items = items;
    }

    // Getters y setters
    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<ItemResponse> getItems() {
        return items;
    }
}
