package com.cocotera.interfaces;

import com.cocotera.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductsRepository extends JpaRepository<Product, String> {
}
