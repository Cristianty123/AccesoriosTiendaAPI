package com.tienda.accesorios.accesoriostiendaapi.dto;

import java.util.List;

public class InvoicePageResponse {
    private int totalPages;
    private int currentPage;
    private List<String> pagesToShow;
    private List<InvoiceResponse> invoices;

    public InvoicePageResponse(int totalPages, int currentPage, List<String> pagesToShow, List<InvoiceResponse> invoices) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pagesToShow = pagesToShow;
        this.invoices = invoices;
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

    public List<String> getPagesToShow() {
        return pagesToShow;
    }

    public void setPagesToShow(List<String> pagesToShow) {
        this.pagesToShow = pagesToShow;
    }

    public List<InvoiceResponse> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceResponse> invoices) {
        this.invoices = invoices;
    }
}
