package com.rab3tech.admin.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.Customer;
import com.rab3tech.dao.entity.CustomerAccountInfo;

/**
 * 
 * @author nagendra
 * 
 * 
 *         Spring Data JPA repository
 * 
 * 
 * 
 */
public interface MagicCustomerRepository extends JpaRepository<Customer, Integer> {
	public Optional<Customer> findByEmail(String email);


}
