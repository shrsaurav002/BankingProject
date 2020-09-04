package com.rab3tech.customer.dao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rab3tech.dao.entity.PayeeInfo;

public interface PayeeRepository extends JpaRepository<PayeeInfo, Integer> {

	@Query("SELECT tt FROM PayeeInfo tt where tt.payeeStatus.id = 3")
	List<PayeeInfo> findPendingPayee();

	@Query("SELECT tt FROM PayeeInfo tt where tt.payeeStatus.id = 2 and tt.customerId=?1")
	List<PayeeInfo> findRegisteredPayee(String email);

	@Query("Select t from PayeeInfo t where t.customerId=?1 and t.payeeName=?2")
	PayeeInfo findPayeeDetails(String username, String name);

	List<PayeeInfo> findByCustomerId(String email);

	Optional<PayeeInfo> findByCustomerIdAndPayeeAccountNo(String custID, String payeeAccountNo);
}
