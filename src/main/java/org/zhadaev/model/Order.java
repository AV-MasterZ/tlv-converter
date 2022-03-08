package org.zhadaev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String dateTime;
    private long orderNumber;
    private String customerName;
    private List<Item> items = new ArrayList<>();

    public String getDateTime() {
        return dateTime;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void addNewItem() {
        items.add(new Item());
    }

    @JsonIgnore
    public Item getLastItem() {
        if (items.isEmpty()) {
            return null;
        }
        return items.get(items.size() - 1);
    }

    public List<Item> getItems() {
        return items;
    }
}
