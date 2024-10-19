package com.cocotera.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
public class OrderItem {
    @Id
    private String orderItemId;
    private String orderId;
    private String productId;
    private int quantity;
    private BigDecimal price;
}
