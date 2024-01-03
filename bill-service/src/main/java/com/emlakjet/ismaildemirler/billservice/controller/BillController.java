package com.emlakjet.ismaildemirler.billservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emlakjet.ismaildemirler.billservice.dto.bill.BillDto;
import com.emlakjet.ismaildemirler.billservice.payload.ServiceResponse;
import com.emlakjet.ismaildemirler.billservice.payload.bill.QueryRequest;
import com.emlakjet.ismaildemirler.billservice.service.bill.BillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bills")
@Tag(name = "Bill", description = "Bill Service Documentation")
public class BillController {
	
	private final BillService billService;

	@PostMapping
	@Operation(summary = "New Bill", description = "Adding New Bill With Limit Control Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The Bill Has Been Successfully Created!"),
            @ApiResponse(responseCode = "200", description = "The Bill Could Not Been Accepted As The Amount Has Been Exceeded The Limit!")
    })
	public ResponseEntity<ServiceResponse> saveBill(@Valid @RequestBody BillDto billDto) {
		return ResponseEntity.created(null)
				.body(ServiceResponse.builder()
						.success(true)
						.message("The Bill Has Been Successfully Created!")
						.error(null)
						.data(billService.saveBill(billDto))
						.build());
	}
	
	@GetMapping
	@Operation(summary = "Get All Bills", description = "Fetching All Bills Method")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill List Has Been Successfully Fetched!"),
            @ApiResponse(responseCode = "204", description = "There Is No Bills To Display!")
    })
	public ResponseEntity<ServiceResponse> getBills() {
		return ResponseEntity.ok(ServiceResponse.builder()
						.success(true)
						.message("Bill List Has Been Successfully Fetched!")
						.error(null)
						.data(billService.getBills())
						.build());
	}
	
	@GetMapping("/{valid}")
	@Operation(summary = "Get Unaccepted/Accepted Bills", description = "Fetching Unaccepted or Accepted Bills By Querying Valid Parameter")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unaccepted/Accepted Bills Have Been Successfully Fetched!")
    })
	public ResponseEntity<ServiceResponse> getBillsByValid(@PathVariable boolean valid) {
		return ResponseEntity.ok(ServiceResponse.builder()
						.success(true)
						.message(valid ? "Accepted Bills Have Been Successfully Fetched!": "Unaccepted Bills Have Been Successfully Fetched!")
						.error(null)
						.data(billService.getBillsByValid(valid))
						.build());
	}
	
	@GetMapping("/getBillsByEmail")
	@Operation(summary = "Get Bills By Email", description = "Fetching Bills By Querying Email Parameter")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill List Has Been Successfully Fetched!")
    })
	public ResponseEntity<ServiceResponse> getBillsByEmail(@RequestBody QueryRequest request) {
		return ResponseEntity.ok(ServiceResponse.builder()
						.success(true)
						.message("Bill List Has Been Successfully Fetched!")
						.error(null)
						.data(billService.getBillsByEmail(request.getEmail()))
						.build());
	}
}
