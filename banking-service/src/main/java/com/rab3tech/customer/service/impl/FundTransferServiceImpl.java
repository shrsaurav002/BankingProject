package com.rab3tech.customer.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.FundTransferRepo;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.FundTransferEntity;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;

@Service
@Transactional
public class FundTransferServiceImpl implements FundTransferService {
	@Autowired
	private CustomerAccountInfoRepository custAccInfoRepo;
	@Autowired
	private FundTransferRepo fundRepo;

	@Override
	public void transfer(FundTransferVO fundTransferVO, LoginVO loginVO) {
		String custId = loginVO.getUsername();
		CustomerAccountInfo customerFrom = custAccInfoRepo
				.findByCustomerUsernameAndAccountType(custId, fundTransferVO.getSentFrom()).get();
		System.out.println(customerFrom);
		String toAccNo = fundTransferVO.getSentTo().substring(0, 9);
		CustomerAccountInfo customerTo = custAccInfoRepo.findByAccountNumber(toAccNo).get();
		customerFrom.setAvBalance(-fundTransferVO.getAmount());
		customerFrom.setTavBalance(customerFrom.getTavBalance() - fundTransferVO.getAmount());
		customerTo.setAvBalance(fundTransferVO.getAmount());
		customerTo.setTavBalance(customerTo.getTavBalance() + fundTransferVO.getAmount());
		FundTransferEntity fundTransferEntity = new FundTransferEntity();
		fundTransferEntity.setSentFrom(customerFrom);
		fundTransferEntity.setSentTo(customerTo);
		fundTransferEntity.setAmount(fundTransferVO.getAmount());
		fundTransferEntity.setRemarks(fundTransferVO.getRemarks());
		fundTransferEntity.setOtp(0);
		fundRepo.save(fundTransferEntity);
	}

}
