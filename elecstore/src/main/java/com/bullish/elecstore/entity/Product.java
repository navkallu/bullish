package com.bullish.elecstore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table (name = "tbl_product")
@Entity
@Setter
@Getter
@ToString
public class Product {
    @Id
    @GeneratedValue (strategy =  GenerationType.IDENTITY)
    private long id;
    private String name;
    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DiscountDeal> discountDeals ;

    public Product() {
        this.discountDeals = new ArrayList<>();
    }

    // Constructors, getters, and setters

    public void addDiscountDeal(DiscountDeal discountDeal) {
        discountDeals.add(discountDeal);
        discountDeal.setProduct(this);
    }

    public void removeDiscountDeal(DiscountDeal discountDeal) {
        discountDeals.remove(discountDeal);
        discountDeal.setProduct(null);
    }

}
