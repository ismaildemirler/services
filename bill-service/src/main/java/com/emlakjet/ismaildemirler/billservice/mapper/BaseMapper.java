package com.emlakjet.ismaildemirler.billservice.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface BaseMapper {
	default byte[] toBytes(String string) {
        return string != null ? string.getBytes() : null;
    }
	
	default String toString(byte[] byteArray) {
        return byteArray != null ? new String(byteArray) : null;
    }
}
