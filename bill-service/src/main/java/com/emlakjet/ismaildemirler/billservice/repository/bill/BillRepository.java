package com.emlakjet.ismaildemirler.billservice.repository.bill;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.ismaildemirler.billservice.entity.bill.Bill;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BillRepository extends JpaRepository<Bill, UUID> {

	Optional<List<Bill>> findByValid(boolean valid);
	Optional<List<Bill>> findByEmail(String email);
	Optional<List<Bill>> findByEmailAndValid(String email, boolean valid);
}
