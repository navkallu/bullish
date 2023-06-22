package com.bullish.elecstore.service;

import com.bullish.elecstore.entity.Basket;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.repository.BasketRepository;
import com.bullish.elecstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToBasket() {
        Long basketId = 1L;
        Long productId = 2L;

        Basket basket = new Basket();
        basket.setId(basketId);

        Product product = new Product();
        product.setId(productId);

        when(basketRepository.findById(eq(basketId))).thenReturn(Optional.of(basket));
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(product));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        customerService.addToBasket(basketId, productId);

        verify(basketRepository, times(1)).findById(basketId);
        verify(productRepository, times(1)).findById(productId);
        verify(basketRepository, times(1)).save(basket);
    }

    @Test
    public void testRemoveFromBasket() {
        Long basketId = 1L;
        Long productId = 2L;

        Basket basket = new Basket();
        basket.setId(basketId);

        Product product = new Product();
        product.setId(productId);

        when(basketRepository.findById(eq(basketId))).thenReturn(Optional.of(basket));
        when(productRepository.findById(eq(productId))).thenReturn(Optional.of(product));
        when(basketRepository.save(any(Basket.class))).thenReturn(basket);

        customerService.removeFromBasket(basketId, productId);

        verify(basketRepository, times(1)).findById(basketId);
        verify(productRepository, times(1)).findById(productId);
        verify(basketRepository, times(1)).save(basket);
    }

   /* @Test
    public void testCalculateReceipt() {
        Long basketId = 1L;
        double expectedReceiptTotal = 25.0;

        Basket basket = new Basket();
        basket.setId(basketId);
        basket.addProductId(1L);
        basket.addProductId(2L);

        Product product1 = new Product();
        product1.setId(1L);
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(15.0);

        when(basketRepository.findById(eq(basketId))).thenReturn(Optional.of(basket));
        when(productRepository.findById(eq(1L))).thenReturn(Optional.of(product1));
        when(productRepository.findById(eq(2L))).thenReturn(Optional.of(product2));

        double actualReceiptTotal = customerService.calculateReceipt(basketId);

        assertEquals(expectedReceiptTotal, actualReceiptTotal);

        verify(basketRepository, times(1)).findById(basketId);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(2L);
    }*/
}
