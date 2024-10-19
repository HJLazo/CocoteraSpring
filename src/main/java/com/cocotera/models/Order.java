package com.cocotera.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "`order`") // Escaped with backticks
@Data
public class Order {
    @Id
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private BigDecimal total;

}
