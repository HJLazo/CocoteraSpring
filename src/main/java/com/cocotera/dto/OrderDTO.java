package com.cocotera.dto;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private String clientId;
    private List<OrderItemDTO> orderItems = new ArrayList<>();


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
