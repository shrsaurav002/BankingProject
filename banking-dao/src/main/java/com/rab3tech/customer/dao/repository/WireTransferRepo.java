package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.WireTransferEntity;

public interface WireTransferRepo extends JpaRepository<WireTransferEntity, Integer> {
	@Query("select t from WireTransferEntity t where t.transferStatus.id=1")
	Optional<List<WireTransferEntity>> findPendingTransfers();

}
