package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.FundTransferEntity;

public interface FundTransferRepo extends JpaRepository<FundTransferEntity, Integer> {
	@Query("select f from FundTransferEntity f where f.sentFrom.customerId.loginid=?1")
	Optional<List<FundTransferEntity>> findBySender(String username);

}
