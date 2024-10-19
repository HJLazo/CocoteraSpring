package com.cocotera.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    private String orderId;
    private String clientId;
    private BigDecimal total;

}
