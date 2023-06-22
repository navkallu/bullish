package com.bullish.elecstore.controller;

import com.bullish.elecstore.CustomException.ConcurrencyConflictException;
import com.bullish.elecstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/basket/{basketId}/products/{productId}")
    public ResponseEntity<String> addToBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        try {
            customerService.addToBasket(basketId, productId);
            return ResponseEntity.ok("Product added to the basket successfully.");
        }catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception exgen){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/basket/{basketId}/productdelete/{productId}/")
    public ResponseEntity<String> removeFromBasket(@PathVariable Long basketId, @PathVariable Long productId) {
        try {
            customerService.removeFromBasket(basketId, productId);
            return ResponseEntity.ok("Product deleted from basket successfully.");
        }catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception exgen){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

     }

    @GetMapping("/basket/{basketId}/receipt")
    //public ResponseEntity<Double> calculateReceipt(@PathVariable Long basketId) {
    public ResponseEntity<String> calculateReceipt(@PathVariable Long basketId) {
        //double receiptTotal = customerService.calculateReceipt(basketId);
        try {
            String receiptTotal = customerService.calculateReceipt(basketId);
            return ResponseEntity.ok(receiptTotal);
        }catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception exgen){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
