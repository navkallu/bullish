package com.bullish.elecstore.service;

import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = adminService.createProduct(product);

        assertEquals(product, createdProduct);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testRemoveProduct() {
        Long productId = 1L;

        doNothing().when(productRepository).deleteById(eq(productId));

        assertDoesNotThrow(() -> adminService.removeProduct(productId));

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testAddDiscountDeal() {
        Long productId = 1L;
        DiscountDeal discountDeal = new DiscountDeal();

        Product product = new Product();
        product.addDiscountDeal(discountDeal);

        when(productRepository.findById(eq(productId))).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = adminService.addDiscountDeal(productId, discountDeal);

        assertEquals(product, updatedProduct);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testRemoveDiscountDeal() {
        Long productId = 1L;
        Long discountDealId = 2L;

        DiscountDeal discountDeal = new DiscountDeal();
        discountDeal.setId(discountDealId);

        Product product = new Product();
        product.addDiscountDeal(discountDeal);

        when(productRepository.findById(eq(productId))).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = adminService.removeDiscountDeal(productId, discountDealId);

        assertEquals(product, updatedProduct);
        assertTrue(product.getDiscountDeals().isEmpty());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product);
    }
}
