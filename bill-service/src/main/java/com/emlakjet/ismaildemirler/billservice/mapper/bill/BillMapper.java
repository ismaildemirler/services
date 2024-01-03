package com.emlakjet.ismaildemirler.billservice.mapper.bill;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.emlakjet.ismaildemirler.billservice.dto.bill.BillDto;
import com.emlakjet.ismaildemirler.billservice.entity.bill.Bill;
import com.emlakjet.ismaildemirler.billservice.mapper.BaseMapper;


@Mapper(componentModel = "spring")
public interface BillMapper extends BaseMapper {

	BillMapper INSTANCE = Mappers.getMapper(BillMapper.class);

	Bill toEntity(BillDto billDto);

	BillDto toDto(Bill bill);
	
	List<BillDto> toListDto(List<Bill> bills);
	
	List<Bill> toListEntity(List<BillDto> billDtos);
}

