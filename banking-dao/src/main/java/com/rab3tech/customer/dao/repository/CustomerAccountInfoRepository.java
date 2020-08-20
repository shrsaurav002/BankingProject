package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.CustomerAccountInfo;

public interface CustomerAccountInfoRepository extends JpaRepository<CustomerAccountInfo, Long> {

}
