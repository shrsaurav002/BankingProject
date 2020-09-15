package com.rab3tech.customer.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountEnquiryRepository;
import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.LoginRepository;
import com.rab3tech.customer.service.CustomerAccountInfoService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.CustomerSaving;
import com.rab3tech.vo.CustomerVO;

@Service
@Transactional
public class CustomerAccountInfoServiceImpl implements CustomerAccountInfoService {
	@Autowired
	private CustomerAccountInfoRepository custAccRepo;
	@Autowired
	private CustomerAccountEnquiryRepository custEnquiry;
	@Autowired
	private LoginRepository loginRepo;

	@Async
	@Override
	public void createNewAccount(CustomerVO customerVO) {
		try {
			Thread.sleep(30000);
			CustomerAccountInfo custAccountInfo = new CustomerAccountInfo();
			CustomerSaving customer = custEnquiry.findByEmail(customerVO.getEmail()).get();
			custAccountInfo.setAccountNumber(generateAccNo());
			custAccountInfo.setCurrency("USD");
			custAccountInfo.setBranch(customer.getLocation());
			custAccountInfo.setCustomerId(loginRepo.findByLoginid(customerVO.getEmail()).get());
			custAccountInfo.setTavBalance(0f);
			custAccountInfo.setAvBalance(0f);
			custAccountInfo.setStatusAsOf(new Date());
			custAccountInfo.setAccountType(customer.getAccType());
			custAccRepo.save(custAccountInfo);
			System.out.println("done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String generateAccNo() {
		long num = 0;
		while (num < Math.pow(10, 8)) {
			num = (long) (Math.random() * Math.pow(10, 9));
		}
		String accNoString = "" + num;
		return accNoString;
	}

}
