package com.bullish.elecstore.controller;

import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        try{
             adminService.createProduct(product);
            return ResponseEntity.ok("Product created successfully.");
            }catch(IllegalArgumentException ex){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch(Exception exgen){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @DeleteMapping("/products/{id}")
    public  ResponseEntity<String> removeProduct(@PathVariable Long id) {
        try{
                adminService.removeProduct(id);
                return ResponseEntity.ok("Product Removed successfully.");
        }catch(IllegalArgumentException ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception exgen){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/products/{id}/discounts")
    public ResponseEntity<String> addDiscountDeal(@PathVariable Long id, @RequestBody DiscountDeal discountDeal) {
        try{
                adminService.addDiscountDeal(id, discountDeal);
                return ResponseEntity.ok("Discount Deal Added successfully.");
            }catch(IllegalArgumentException ex){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch(Exception exgen){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @DeleteMapping("/products/{productId}/discounts/{discountId}")
    public ResponseEntity<String> removeDiscountDeal(@PathVariable Long productId, @PathVariable Long discountId) {
        try{
                adminService.removeDiscountDeal(productId, discountId);
                return ResponseEntity.ok("Discount Deal Removed successfully.");
            }catch(IllegalArgumentException ex){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }catch(Exception exgen){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }
}
