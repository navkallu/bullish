package com.bullish.elecstore.controller;

import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product created successfully.");

        when(adminService.createProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<String> actualResponse  = adminController.createProduct(product);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(adminService, times(1)).createProduct(product);
    }

    @Test
    public void testRemoveProduct() {
        Long productId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product Removed successfully.");
        doNothing().when(adminService).removeProduct(eq(productId));

        ResponseEntity<String> actualResponse = adminController.removeProduct(productId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(adminService, times(1)).removeProduct(productId);
    }

    @Test
    public void testAddDiscountDeal() {
        Long productId = 1L;
        DiscountDeal discountDeal = new DiscountDeal();

        Product product = new Product();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Discount Deal Added successfully.");

        when(adminService.addDiscountDeal(eq(productId), any(DiscountDeal.class))).thenReturn(product);

        ResponseEntity<String> actualResponse = adminController.addDiscountDeal(productId, discountDeal);
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

       verify(adminService, times(1)).addDiscountDeal(productId, discountDeal);
    }

    @Test
    public void testRemoveDiscountDeal() {
        Long productId = 1L;
        Long discountDealId = 2L;

        Product product = new Product();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Discount Deal Removed successfully.");
        when(adminService.removeDiscountDeal(eq(productId), eq(discountDealId))).thenReturn(product);

        ResponseEntity<String> actualResponse = adminController.removeDiscountDeal(productId, discountDealId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(adminService, times(1)).removeDiscountDeal(productId, discountDealId);
    }
}
