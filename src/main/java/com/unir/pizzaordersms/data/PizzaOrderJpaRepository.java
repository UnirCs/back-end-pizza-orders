package com.unir.pizzaordersms.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unir.pizzaordersms.model.sql.PizzaOrder;

import java.util.Optional;

interface PizzaOrderJpaRepository extends JpaRepository<PizzaOrder, Long> {
    Optional<PizzaOrder> findBySessionId(String sessionId);

}
