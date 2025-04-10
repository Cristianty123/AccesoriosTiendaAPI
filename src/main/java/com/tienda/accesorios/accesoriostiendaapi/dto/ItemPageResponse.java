package com.tienda.accesorios.accesoriostiendaapi.dto;

import java.util.List;

public class ItemPageResponse {
    private int totalPages;
    private int currentPage;
    private List<String> pagesToShow;
    private List<ItemResponse> items;

    public ItemPageResponse(int totalPages, int currentPage,List<String> pagesToShow, List<ItemResponse> items) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pagesToShow = pagesToShow;
        this.items = items;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }

    public List<String> getPagesToShow() {
        return pagesToShow;
    }

    public void setPagesToShow(List<String> pagesToShow) {
        this.pagesToShow = pagesToShow;
    }
}
