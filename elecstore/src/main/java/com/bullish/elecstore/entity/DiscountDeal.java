package com.bullish.elecstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tbl_discountdeal")
@Entity
@Setter
@Getter
@ToString
public class DiscountDeal {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private int buyQuantity;
    private double discountPercentage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

}
