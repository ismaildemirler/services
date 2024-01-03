package com.emlakjet.ismaildemirler.billservice.service.bill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emlakjet.ismaildemirler.billservice.dto.bill.BillDto;
import com.emlakjet.ismaildemirler.billservice.entity.bill.Bill;
import com.emlakjet.ismaildemirler.billservice.mapper.bill.BillMapper;
import com.emlakjet.ismaildemirler.billservice.repository.bill.BillRepository;
import com.emlakjet.ismaildemirler.billservice.repository.user.UserRepository;
import com.emlakjet.ismaildemirler.billservice.util.exception.DataNotFoundException;
import com.emlakjet.ismaildemirler.billservice.util.exception.LimitException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
	
	@Value("${bill.amount.limit}")
	private int limit;
	
	private final BillMapper billMapper;
	private final UserRepository userRepository;
	private final BillRepository billRepository;

	@Override
	public BillDto saveBill(BillDto billDto) {
		var user = userRepository.findByEmail(billDto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("There is no user registered by this email!"));
		Optional<List<Bill>> bills = billRepository.findByEmailAndValid(user.getEmail(), true);
		Bill bill = Bill.builder()
				.billId(UUID.randomUUID())
				.firstName(billDto.getEmail())
				.lastName(billDto.getLastName())
				.email(billDto.getEmail())
				.amount(billDto.getAmount())
				.productName(billDto.getProductName())
				.user(user)
				.build();
		if(!bills.isEmpty()) {
			int total = bills.get().stream().map(m -> m.getAmount()).reduce(0, Integer::sum);
			if(total + billDto.getAmount() > limit) {				
				bill.setValid(false);
				billRepository.save(bill);				
				throw new LimitException("The Bill Could Not Have Been Accepted As The Amount Has Been Exceeded The Limit!");
			}
		}
		if(billDto.getAmount() > limit) {
			bill.setValid(false);
			billRepository.save(bill);				
			throw new LimitException("The Bill Could Not Been Accepted As The Amount Has Been Exceeded The Limit!");
		}
		bill.setValid(true);
		return billMapper.toDto(billRepository.save(bill));	
	}
	
	@Override
	public List<BillDto> getBillsByEmail(String email) {
		List<Bill> bills = billRepository.findByEmail(email).get();
		if(bills.size() == 0) {
			throw new DataNotFoundException("There Is No Bills To Display!");
		}
		return billMapper.toListDto(bills);
	}
	
	@Override
	public List<BillDto> getBills() {
		List<Bill> bills = billRepository.findAll();
		if(bills.size() == 0) {
			throw new DataNotFoundException("There Is No Bills To Display!");
		}
		return billMapper.toListDto(bills);
	}

	@Override
	public List<BillDto> getBillsByValid(boolean valid) {
		List<Bill> bills = billRepository.findByValid(valid).get();
		if(bills.size() == 0) {
			throw new DataNotFoundException("There Is No Bills To Display!");
		}
		return billMapper.toListDto(bills);
	}
}
