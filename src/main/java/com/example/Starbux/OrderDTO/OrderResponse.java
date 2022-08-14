package com.example.Starbux.OrderDTO;

import java.util.List;

import com.example.Starbux.OrderDTO.OrderItem.OrderItem;

public class OrderResponse {
    private String message;
    private float total;
    private float discountedTotal;
    private int invoiceNumber;
    private String date;
    private List<OrderItem> items;
    private Long orderId;

    public float getTotal() {
        return total;
    }
    public float getdiscountedTotal() {
        return discountedTotal;
    }
    public void setTotal(float amount) {
        this.total = amount;
    }
    public void setdiscountedTotal(float amount) {
        this.discountedTotal = amount;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDate() {
        return date;
    }
    public String getMessage() {
        return message;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
