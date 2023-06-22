package com.bullish.elecstore.controller;

import com.bullish.elecstore.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToBasket() {
        Long basketId = 1L;
        Long productId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product added to the basket successfully.");

        doNothing().when(customerService).addToBasket(anyLong(), anyLong());

        ResponseEntity<String> actualResponse = customerController.addToBasket(basketId, productId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(customerService, times(1)).addToBasket(basketId, productId);
    }

    @Test
    public void testRemoveFromBasket() {
        Long basketId = 1L;
        Long productId = 2L;

        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product deleted from basket successfully.");

        doNothing().when(customerService).removeFromBasket(anyLong(), anyLong());

        ResponseEntity<String> actualResponse = customerController.removeFromBasket(basketId, productId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(customerService, times(1)).removeFromBasket(basketId, productId);
    }

    @Test
    public void testCalculateReceipt() {
        Long basketId = 1L;
        //double receiptTotal = 10.0;
        String receiptTotal = "10.0";

        //ResponseEntity<Double> expectedResponse = ResponseEntity.ok(receiptTotal);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok(receiptTotal);

        when(customerService.calculateReceipt(anyLong())).thenReturn(receiptTotal);

        //ResponseEntity<Double> actualResponse = customerController.calculateReceipt(basketId);
        ResponseEntity<String> actualResponse = customerController.calculateReceipt(basketId);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(customerService, times(1)).calculateReceipt(basketId);
    }
}
