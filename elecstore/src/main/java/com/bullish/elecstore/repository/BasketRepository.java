package com.bullish.elecstore.repository;

import com.bullish.elecstore.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket,Long> {
}
