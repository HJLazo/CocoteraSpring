package com.cocotera.interfaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cocotera.models.OrderItem;

@Repository
public interface IOrderItemRepository  extends JpaRepository<OrderItem, String>{
}
