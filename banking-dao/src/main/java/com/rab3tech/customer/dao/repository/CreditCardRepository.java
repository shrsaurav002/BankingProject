package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.rab3tech.dao.entity.CreditCardEntity;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {
	public Optional<CreditCardEntity> findByEmail(String email);
@Modifying
	public void deleteByEmail(String email);

}
