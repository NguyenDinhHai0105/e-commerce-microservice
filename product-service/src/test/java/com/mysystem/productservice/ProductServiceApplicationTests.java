package com.mysystem.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysystem.productservice.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers //để Junit 5 hiểu là use container để chạy test
class ProductServiceApplicationTests {

	@Container // Junit5 hiểu đây là mongo container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");

	// Mockmcv cung cấp mocked serverlet env, để gọi endpoint
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;

	@Autowired
	public ProductServiceApplicationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
	}


	@DynamicPropertySource // thêm properties vào spring context khi chạy test
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {

		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
					.contentType(MediaType.APPLICATION_JSON)
					.content(productRequestString))
				.andExpect(status().isCreated());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 13")
				.description("iphone 13 is a shit")
				.price(BigDecimal.valueOf(13000))
				.build();
	}

}
