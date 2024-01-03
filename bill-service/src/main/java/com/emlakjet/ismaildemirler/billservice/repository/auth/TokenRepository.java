package com.emlakjet.ismaildemirler.billservice.repository.auth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.emlakjet.ismaildemirler.billservice.entity.auth.Token;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, UUID> {

	@Query(value = """
			select t from token t inner join t.user u\s
			on t.user.userId = u.userId\s
			where u.userId = :userId and (t.expired = false or t.revoked = false)\s
			""")
	List<Token> findAllValidTokenByUser(UUID userId);

	Optional<Token> findByAuthToken(String authToken);
}
