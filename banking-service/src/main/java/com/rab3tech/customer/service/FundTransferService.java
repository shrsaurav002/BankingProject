package com.rab3tech.customer.service;

import java.util.List;

import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.RemittanceVO;
import com.rab3tech.vo.WireTransferVO;

public interface FundTransferService {

	void transfer(FundTransferVO fundTransferVO, LoginVO loginVO);

	List<FundTransferVO> findTransactionByUser(String username);

	String convertToWords(String amount);

	void wireTransferInit(WireTransferVO wireTransferVO);

	void remittanceService(RemittanceVO remittanceVO, String username);

}
