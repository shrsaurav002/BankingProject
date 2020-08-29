package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.AccountTypeVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerUpdateVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.RoleVO;

public interface CustomerService {

	CustomerVO createAccount(CustomerVO customerVO);

	CustomerAccountInfoVO createBankAccount(int csaid);

	List<CustomerVO> findCustomers();

	byte[] findPhotoByid(int cid);

	void updateProfile(CustomerUpdateVO customerVO);
	
	public List<RoleVO> getRoles();

	CustomerVO searchCustomer(String name);
	
	List<AccountTypeVO> findAccountTypes();

	String findCustomerByEmail(String email);

	String findCustomerByMobile(String mobile);

	int addPayee(PayeeInfoVO payeeInfoVO, String payeeEmail);

	List<PayeeInfoVO> pendingPayeeList(String email);

	List<PayeeInfoVO> registeredPayeeList();

	List<CustomerVO> searchCustomers(String searchText);

	byte[] imageSearch(String email);

	CustomerVO findByEmail(String email);

	void updatePayee(String username, String name,String type);

	int findPayeeUrn(String payeeName, String customerId);

	

}
