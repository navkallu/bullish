package com.bullish.elecstore.service;

import com.bullish.elecstore.CustomException.ConcurrencyConflictException;
import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final ProductRepository productRepository;

    @Autowired
    public AdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(Product product) {
        try {
                return productRepository.save(product);
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    @Transactional
    public void removeProduct(Long productId) {
        try {
                productRepository.deleteById(productId);
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    @Transactional
    public Product addDiscountDeal(Long productId, DiscountDeal discountDeal) {
        try {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
                product.addDiscountDeal(discountDeal);
                return productRepository.save(product);
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }

    @Transactional
    public Product removeDiscountDeal(Long productId, Long discountDealId) {

        try {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
                DiscountDeal discountDeal = product.getDiscountDeals().stream()
                        .filter(dd -> dd.getId().equals(discountDealId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Invalid discount deal ID: " + discountDealId));
                product.removeDiscountDeal(discountDeal);
                return productRepository.save(product);
        }catch(ObjectOptimisticLockingFailureException e) {
            // Handle concurrency conflict
            throw new ConcurrencyConflictException("Concurrency conflict occurred. Please try again.");
        }
    }
}
