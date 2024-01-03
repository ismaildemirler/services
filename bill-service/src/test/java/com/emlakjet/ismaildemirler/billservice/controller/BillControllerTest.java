package com.emlakjet.ismaildemirler.billservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.emlakjet.ismaildemirler.billservice.dto.bill.BillDto;
import com.emlakjet.ismaildemirler.billservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.payload.auth.LoginRequest;
import com.emlakjet.ismaildemirler.billservice.payload.bill.QueryRequest;
import com.emlakjet.ismaildemirler.billservice.service.bill.BillService;
import com.emlakjet.ismaildemirler.billservice.util.exception.LimitException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Tag("Bill Service Unit Tests")
@DisplayName("Unit Tests For Bill Endpoints")
public class BillControllerTest {

	String authtoken;
		
	@Autowired
	private MockMvc mockMvc;
		
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private BillService billService;
		
	private static final String ENDPOINT_PATH = "/api/v1/bills";
	
	@Value("${endpoint.basic-url}")
	private String BASIC_URL;
	
	@BeforeEach
	public void setUp() throws Exception {
		LoginRequest loginRequest = LoginRequest.builder()
			.email("ismail.demirler@gmail.com")
			.password("123")
			.build();
		
		String requestBody = objectMapper.writeValueAsString(loginRequest);
		ResultActions resultActions = mockMvc
				.perform(post(this.BASIC_URL + "api/v1/auth")
						.accept(MediaType.APPLICATION_JSON)
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON));
		
		MvcResult mvcResult = resultActions.andDo(print()).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		JSONObject jsonContent = new JSONObject(content);
		this.authtoken = "Bearer " + jsonContent.getJSONObject("data").getString("authToken");
	}
	
	@Test
	@DisplayName("Testing If CreateBill Endpoint Returns 201 CREATED Status")
	public void BillController_CreateBill_ReturnCreated() throws Exception {
		
		UUID billId = UUID.randomUUID();		
		BillDto mockBill = BillDto.builder()
				.billId(billId)
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.firstName("ismail")
				.lastName("demirler")
				.productName("Laptop")
				.build();
		
		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("The Bill Has Been Successfully Created!")
				.error(null)
				.data(mockBill)
				.build();
		
		Mockito.when(billService.saveBill(Mockito.any(BillDto.class))).thenReturn(mockBill);

		String requestBody = objectMapper.writeValueAsString(mockBill);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(ENDPOINT_PATH)
				.header("Authorization", this.authtoken)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}		
	
	@Test
	@DisplayName("Testing If CreateBill Endpoint Returns 406 NOT_ACCEPTABLE Status")
	public void BillController_CreateBill_ReturnNotAcceptable() throws Exception {
		
		UUID billId = UUID.randomUUID();
		BillDto mockBill1 = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.productName("Laptop")
				.build();
		
		billId = UUID.randomUUID();
		BillDto mockBill2 = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(300)
				.email("ismail.demirler@gmail.com")
				.productName("Phone")
				.build();
		
		List<BillDto> bills = List.of(mockBill1, mockBill2);
		Mockito.when(billService.getBills()).thenReturn(bills);
		
		billId = UUID.randomUUID();
		BillDto mockBill3 = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.productName("Bicycle")
				.build();
		
		Mockito.when(billService.saveBill(Mockito.any(BillDto.class))).thenThrow(LimitException.class);

		String requestBody = objectMapper.writeValueAsString(mockBill3);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(ENDPOINT_PATH)
				.header("Authorization", this.authtoken)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
	}
		
	@Test
	@DisplayName("Testing If GetBills Endpoint Returns 200 OK Status")
	public void BillController_GetBills_ReturnOK() throws Exception {
				
		UUID billId = UUID.randomUUID();
		BillDto mockBill1 = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.productName("Laptop")
				.build();
		
		billId = UUID.randomUUID();
		BillDto mockBill2 = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(300)
				.email("ismail.demirler@gmail.com")
				.productName("Phone")
				.build();
		
		List<BillDto> bills = List.of(mockBill1, mockBill2);

		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("Bill List Has Been Successfully Fetched!")
				.error(null)
				.data(bills)
				.build();
		
		Mockito.when(billService.getBills()).thenReturn(bills);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH)
											.header("Authorization", this.authtoken)
											.accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
				
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}

	@Test
	@DisplayName("Testing If GetBillsByValid Endpoint Returns 200 OK Status")
	public void BillController_GetBillsByValid_ReturnOK() throws Exception {
				
		UUID billId = UUID.randomUUID();
		BillDto mockBill = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.productName("Laptop")
				.valid(false)
				.build();
		
		List<BillDto> bills = List.of(mockBill);

		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("Unaccepted Bills Have Been Successfully Fetched!")
				.error(null)
				.data(bills)
				.build();
		
		Mockito.when(billService.getBillsByValid(false)).thenReturn(bills);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH + "/false")
											.header("Authorization", this.authtoken)
											.accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
						
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}

	@Test
	@DisplayName("Testing If GetBillsByEmail Endpoint Returns 200 OK Status")
	public void BillController_GetBillsByEmail_ReturnOK() throws Exception {
				
		UUID billId = UUID.randomUUID();
		BillDto mockBill = BillDto.builder()
				.billId(billId)
				.firstName("ismail")
				.lastName("demirler")
				.amount(500)
				.email("ismail.demirler@gmail.com")
				.productName("Laptop")
				.valid(false)
				.build();
		
		List<BillDto> bills = List.of(mockBill);

		ServiceResponse serviceResponse = ServiceResponse.builder()
				.success(true)
				.message("Bill List Has Been Successfully Fetched!")
				.error(null)
				.data(bills)
				.build();
		
		QueryRequest queryRequest = QueryRequest.builder()
				.email("ismail.demirler@gmail.com")
				.build();
		
		String requestBody = objectMapper.writeValueAsString(queryRequest);
		
		Mockito.when(billService.getBillsByEmail("ismail.demirler@gmail.com")).thenReturn(bills);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get(ENDPOINT_PATH + "/getBillsByEmail")
											.content(requestBody)
											.header("Authorization", this.authtoken)
											.accept(MediaType.APPLICATION_JSON)
											.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
						
		String expected = objectMapper.writeValueAsString(serviceResponse);
		JSONAssert.assertEquals(expected, response.getContentAsString(), false);
	}
}
