package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.CustomerAccountInfo;

public interface CustomerAccountInfoRepository extends JpaRepository<CustomerAccountInfo, Long> {
	Optional<CustomerAccountInfo> findByAccountNumber(String num);

	@Query("select t from CustomerAccountInfo t where t.customerId.loginid=?1 AND t.accountType.name=?2")
	CustomerAccountInfo findByCustomerIdAndAccountType(String loginid, String accountType);

	@Query("select t from CustomerAccountInfo t where t.customerId.loginid=?1")
	CustomerAccountInfo findByCustomerId(String loginid);

	@Query("select t from CustomerAccountInfo t where t.customerId.loginid=?1")
	List<CustomerAccountInfo> findAllByCustomerId(String login);

	@Query("select t from CustomerAccountInfo t where t.customerId.loginid=?1 AND accountNumber=?2")
	CustomerAccountInfo findByCustomerIdAndAccountNumber(String loginid, String number);

	@Query("Select t from CustomerAccountInfo t where t.customerId.loginid=?1 and t.accountType.name=?2")
	Optional<CustomerAccountInfo> findByCustomerUsernameAndAccountType(String custId, String accType);

	@Query("select tt from CustomerAccountInfo tt where tt.customerId.loginid=?1 and tt.accountType.name=?2")
	CustomerAccountInfo findByIdAndAccType(String loginid, String accountType);

	@Query("delete from CustomerAccountInfo c where c.customerId.loginid=?1")
	@Modifying
	void deleteByCustomer(String email);

}
