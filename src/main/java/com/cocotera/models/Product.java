package com.cocotera.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="product")
public class Product {
    @Id
    private String productId;
    private String name;
    private String description;
    private String url;
    private double price;
    private int stockQuantity;
}
