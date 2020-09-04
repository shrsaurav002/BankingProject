package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.CreditCardEntity;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {
	public Optional<CreditCardEntity> findByEmail(String email);

	public void deleteByEmail(String email);

}
