package com.emlakjet.ismaildemirler.billservice.service.bill;

import java.util.List;

import com.emlakjet.ismaildemirler.billservice.dto.bill.BillDto;

public interface BillService {

	public BillDto saveBill(BillDto billDto);
	public List<BillDto> getBills();
	public List<BillDto> getBillsByEmail(String email);
	public List<BillDto> getBillsByValid(boolean valid);
}
