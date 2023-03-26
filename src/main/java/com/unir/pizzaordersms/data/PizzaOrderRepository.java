package com.unir.pizzaordersms.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.pizzaordersms.model.sql.PizzaOrder;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrder, Long> {


}
