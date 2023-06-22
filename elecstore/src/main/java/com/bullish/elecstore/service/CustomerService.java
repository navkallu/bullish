package com.bullish.elecstore.service;

import com.bullish.elecstore.CustomException.ConcurrencyConflictException;
import com.bullish.elecstore.entity.Basket;
import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.repository.BasketRepository;
import com.bullish.elecstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomerService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    public CustomerService(BasketRepository basketRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addToBasket(Long basketId, Long productId) {
       try {
               Basket basket = basketRepository.findById(basketId).orElseGet(() -> {
                   Basket newBasket = new Basket();
                   newBasket.setId(basketId);
                   return basketRepository.save(newBasket);
               });

               Product product = productRepository.findById(productId)
                       .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

               basket.addProductId(productId);
               basketRepository.save(basket);
       }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    @Transactional
    public void removeFromBasket(Long basketId, Long productId) {
        try {
                Basket basket = basketRepository.findById(basketId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid basket ID: " + basketId));

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

                basket.removeProductId(productId);
                basketRepository.save(basket);
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    @Transactional
    //public double calculateReceipt(Long basketId) {
    public String calculateReceipt(Long basketId) {
        final String space = "          ";
        try {
            Basket basket = basketRepository.findById(basketId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid basket ID: " + basketId));

            StringBuilder receiptStr = new StringBuilder();

            receiptStr.append("Product").append(space).append("Qty").append(space).append("Discount").append(space).append("Price");
            receiptStr.append("\n");
            receiptStr.append("--------------------------------------------------------------------------------------------------");
            receiptStr.append("\n");
            double total = 0.0;
            Map<Long, Integer> productCount = new HashMap<>();

            // Count the occurrences of each product ID in the basket
            for (Long productId : basket.getProductIds()) {
                productCount.put(productId, productCount.getOrDefault(productId, 0) + 1);
            }

            // Calculate the total price with discounts applied
            for (Map.Entry<Long, Integer> entry : productCount.entrySet()) {
                Long productId = entry.getKey();
                Integer quantity = entry.getValue();

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

                receiptStr.append(product.getName()).append(space).append(quantity).append(space);

                total += calculateProductPrice(product, quantity, receiptStr);
                receiptStr.append("\n");
            }

            receiptStr.append("--------------------------------------------------------------------------------------------------");
            receiptStr.append("\n");
            receiptStr.append("------------TotalPrice = "+total+"------------");
            receiptStr.append("\n");
            receiptStr.append("--------------------------------------------------------------------------------------------------");

            receiptStr.append("\n");
            return receiptStr.toString();
            //return total;
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    private double calculateProductPrice(Product product, int quantity, StringBuilder receiptStr ) {
        double totalPrice = 0.0;
        final String space = "          ";

        // Check if there are any discount deals for the product
        List<DiscountDeal> discountDeals = product.getDiscountDeals();

        if (discountDeals.isEmpty()) {
            // No discount deals, calculate the total price without discounts
            totalPrice = product.getPrice() * quantity;
            receiptStr.append("N/A    ");
        } else {
            // Apply discount deals if available
            int remainingQuantity = quantity;

            for (DiscountDeal discountDeal : discountDeals) {
                int buyQuantity = discountDeal.getBuyQuantity();
                double discountPercentage = discountDeal.getDiscountPercentage();

                int numDiscountedSets = remainingQuantity / buyQuantity;
                int remainingItems = remainingQuantity % buyQuantity;

                double discountedPrice = product.getPrice() * (1 - (discountPercentage / 100));
                totalPrice += (numDiscountedSets * (discountedPrice * buyQuantity) + remainingItems * product.getPrice());

                remainingQuantity = remainingItems;
            }
            receiptStr.append("Applied");
        }
        receiptStr.append(space).append(totalPrice);
        return totalPrice;
    }
}
