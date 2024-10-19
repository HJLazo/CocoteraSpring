package com.cocotera.interfaces;
import com.cocotera.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cocotera.models.Order;

@Repository
public interface IOrderRepository extends JpaRepository<Order, String> {
}
