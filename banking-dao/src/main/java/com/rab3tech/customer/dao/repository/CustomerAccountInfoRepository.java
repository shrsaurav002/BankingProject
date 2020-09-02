package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.Login;

public interface CustomerAccountInfoRepository extends JpaRepository<CustomerAccountInfo, Long> {
	Optional<CustomerAccountInfo> findByAccountNumber(String num);

	CustomerAccountInfo findByCustomerId(Login login);

	List<CustomerAccountInfo> findAllByCustomerId(Login login);

	CustomerAccountInfo findByCustomerIdAndAccountNumber(Login login, String number);

	@Query("Select t from CustomerAccountInfo t where t.customerId.loginid=?1 and t.accountType.name=?2")
	Optional<CustomerAccountInfo> findByCustomerUsernameAndAccountType(String custId,String accType);

}
