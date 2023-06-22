package com.bullish.elecstore;

import com.bullish.elecstore.controller.AdminController;
import com.bullish.elecstore.controller.CustomerController;
import com.bullish.elecstore.entity.DiscountDeal;
import com.bullish.elecstore.entity.Product;
import com.bullish.elecstore.service.AdminService;
import com.bullish.elecstore.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = {AdminController.class, CustomerController.class})
public class ElecstoreApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdminService adminService;

	@MockBean
	private CustomerService customerService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateProduct() throws Exception {
		Product product = new Product();
		product.setId(1L);
		product.setName("Test Product");
		product.setPrice(9.99);

		when(adminService.createProduct(any(Product.class))).thenReturn(product);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/admin/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\": \"Test Product\", \"price\": 9.99}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product created successfully."));

		verify(adminService, times(1)).createProduct(any(Product.class));
	}

	@Test
	public void testRemoveProduct() throws Exception {
		Long productId = 1L;

		doNothing().when(adminService).removeProduct(eq(productId));

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/admin/products/{id}", productId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product Removed successfully."));

		verify(adminService, times(1)).removeProduct(eq(productId));
	}

	@Test
	public void testAddDiscountDeal() throws Exception {
		Long productId = 1L;
		DiscountDeal discountDeal = new DiscountDeal();
		discountDeal.setId(1L);
		discountDeal.setBuyQuantity(2);
		discountDeal.setDiscountPercentage(10.0);

		Product product = new Product();
		product.setId(productId);
		product.setName("Test Product");
		product.setPrice(9.99);

		when(adminService.addDiscountDeal(eq(productId), any(DiscountDeal.class))).thenReturn(product);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/admin/products/{id}/discounts", productId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"buyQuantity\": 2, \"discountPercentage\": 10.0}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Discount Deal Added successfully."));

		verify(adminService, times(1)).addDiscountDeal(eq(productId), any(DiscountDeal.class));
	}

	@Test
	public void testRemoveDiscountDeal() throws Exception {
		Long productId = 1L;
		Long discountDealId = 2L;

		Product product = new Product();
		product.setId(productId);
		product.setName("Test Product");
		product.setPrice(9.99);

		DiscountDeal discountDeal = new DiscountDeal();
		discountDeal.setId(discountDealId);
		discountDeal.setBuyQuantity(2);
		discountDeal.setDiscountPercentage(10.0);

		product.setDiscountDeals(Collections.singletonList(discountDeal));

		when(adminService.removeDiscountDeal(eq(productId), eq(discountDealId))).thenReturn(product);

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/admin/products/{productId}/discounts/{discountId}", productId, discountDealId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Discount Deal Removed successfully."));

		verify(adminService, times(1)).removeDiscountDeal(eq(productId), eq(discountDealId));
	}

	@Test
	public void testAddToBasket() throws Exception {
		doNothing().when(customerService).addToBasket(anyLong(), anyLong());

		mockMvc.perform(MockMvcRequestBuilders
						.post("/customer/basket/{basketId}/products/{productId}", 1L, 2L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product added to the basket successfully."));

		verify(customerService, times(1)).addToBasket(anyLong(), anyLong());
	}

	@Test
	public void testRemoveFromBasket() throws Exception {
		doNothing().when(customerService).removeFromBasket(anyLong(), anyLong());

		mockMvc.perform(MockMvcRequestBuilders
						.delete("/customer/basket/{basketId}/productdelete/{productId}/", 1L, 1L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted from basket successfully."));

		verify(customerService, times(1)).removeFromBasket(anyLong(), anyLong());
	}


	@Test
	public void testCalculateReceipt() throws Exception {
		String receiptTotal = "19.99";

		when(customerService.calculateReceipt(anyLong())).thenReturn(receiptTotal);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/customer/basket/{basketId}/receipt", 1L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("19.99"));

		verify(customerService, times(1)).calculateReceipt(anyLong());
	}

	// TODO :Add more test methods for other endpoints

}