package com.emlakjet.ismaildemirler.billservice.repository.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.ismaildemirler.billservice.entity.user.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByEmail(String email);
}
