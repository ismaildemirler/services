package com.emlakjet.ismaildemirler.productservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.emlakjet.ismaildemirler.productservice.dto.product.ProductDto;
import com.emlakjet.ismaildemirler.productservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.productservice.service.product.ProductService;
import com.emlakjet.ismaildemirler.productservice.util.exception.DataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Tag("Product Service Unit Tests")
@DisplayName("Unit Tests For Product Endpoints")
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ProductService productService;
	
	private static final String ENDPOINT_PATH = "/api/v1/products";
	
	@Test
	@DisplayName("Testing If GetProduct Endpoint Returns 404 NOT_FOUND Status")
	public void ProductController_GetProduct_ReturnNotFound() throws Exception {
		
		UUID productId = UUID.randomUUID();		
		
		Mockito.when(productService.getProduct(productId)).thenThrow(DataNotFoundException.class);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH + "/" + productId)
											.accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

	}	
	
	@Test
	@DisplayName("Testing If GetProduct Endpoint Returns 200 OK Status")
	public void ProductController_GetProduct_ReturnOK() throws Exception {
				
		UUID productId = UUID.randomUUID();
		ProductDto mockProduct = ProductDto.builder()
				.productId(productId)
				.name("Random Product")
				.price(3000)
				.status(true)
				.build();
		
		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("Product Has Been Successfully Retrieved!")
				.error(null)
				.data(mockProduct)
				.build();
		
		Mockito.when(productService.getProduct(productId)).thenReturn(mockProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH + "/" + productId)
											.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
				
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}
	
	@Test
	@DisplayName("Testing If GetProductList Endpoint Returns 204 NO_CONTENT Status")
	public void ProductController_GetProductList_ReturnNoContent() throws Exception {
		
		Mockito.when(productService.getProducts()).thenReturn(new ArrayList<>());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH)
											.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

	}	
	
	@Test
	@DisplayName("Testing If GetProductList Endpoint Returns 200 OK Status")
	public void ProductController_GetProductList_ReturnOK() throws Exception {
				
		UUID productId = UUID.randomUUID();
		ProductDto mockProduct1 = ProductDto.builder()
				.productId(productId)
				.name("Random Product 1")
				.price(3000)
				.status(true)
				.build();
		
		productId = UUID.randomUUID();
		ProductDto mockProduct2 = ProductDto.builder()
				.productId(productId)
				.name("Random Product 2")
				.price(5000)
				.status(true)
				.build();
		
		List<ProductDto> products = List.of(mockProduct1, mockProduct2);

		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("Product List Has Been Successfully Fetched!")
				.error(null)
				.data(products)
				.build();
		
		Mockito.when(productService.getProducts()).thenReturn(products);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH)
											.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
				
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}
	
	@Test
	@DisplayName("Testing If SaveProduct Endpoint Returns 201 CREATED Status")
	public void ProductController_SaveProduct_ReturnCreated() throws Exception {
		
		UUID productId = UUID.randomUUID();		
		ProductDto mockProduct = ProductDto.builder()
				.productId(productId)
				.name("Laptop")
				.price(35000)
				.status(true)
				.build();
		
		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("The Product Has Been Successfully Added!")
				.error(null)
				.data(mockProduct)
				.build();
		
		Mockito.when(productService.saveProduct(Mockito.any(ProductDto.class))).thenReturn(mockProduct);

		String requestBody = objectMapper.writeValueAsString(mockProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(ENDPOINT_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(String.format("http://144.126.238.36/api/v1/products/%s", productId),
				response.getHeader(HttpHeaders.LOCATION));
		
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);

	}		

	@Test
	@DisplayName("Testing If UpdateProduct Endpoint Returns 201 CREATED Status")
	public void ProductController_UpdateProduct_ReturnCreated() throws Exception {
		
		UUID productId = UUID.randomUUID();
		ProductDto mockProduct = ProductDto.builder()
				.productId(productId)
				.name("Random Product")
				.price(3000)
				.status(true)
				.build();
		
		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("The Product Has Been Successfully Updated!")
				.error(null)
				.data(mockProduct)
				.build();
		
		Mockito.when(productService.updateProduct(mockProduct)).thenReturn(mockProduct);
		
		String requestBody = objectMapper.writeValueAsString(mockProduct);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.put(ENDPOINT_PATH)
											.accept(MediaType.APPLICATION_JSON)
											.content(requestBody)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(String.format("http://localhost/api/v1/products/%s", productId),
				response.getHeader(HttpHeaders.LOCATION));
		
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}	
	
	@Test
	@DisplayName("Testing If DeleteProduct Endpoint Returns 404 NOT_FOUND Status")
	public void ProductController_DeleteProduct_ReturnNotFound() throws Exception {
		
		UUID productId = UUID.randomUUID();	
		
		Mockito.doThrow(DataNotFoundException.class).when(productService).deleteProduct(productId);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.delete(ENDPOINT_PATH + "/" + productId)
											.accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}	
	
	@Test
	@DisplayName("Testing If DeleteProduct Endpoint Returns 200 OK Status")
	public void testDeleteProductShouldReturn200OK() throws Exception {
		
		UUID productId = UUID.randomUUID();	
		
		Mockito.doNothing().when(productService).deleteProduct(productId);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.delete(ENDPOINT_PATH + "/" + productId)
											.accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}

