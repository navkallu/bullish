package com.bullish.elecstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "tbl_basket")
@Entity
@Setter
@Getter
@ToString
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<Long> productIds;

    // Constructors, getters, and setters

    public Basket() {
        this.productIds = new ArrayList<>();
    }

    public void addProductId(Long productId) {
        productIds.add(productId);
    }

    public void removeProductId(Long productId) {
        productIds.remove(productId);
    }

}


