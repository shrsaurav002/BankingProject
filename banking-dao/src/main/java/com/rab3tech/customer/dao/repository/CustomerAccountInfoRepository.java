package com.rab3tech.customer.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.CustomerAccountInfo;

public interface CustomerAccountInfoRepository extends JpaRepository<CustomerAccountInfo, Long> {
	Optional<CustomerAccountInfo> findByAccountNumber(String num);

}
