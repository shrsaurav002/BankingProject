package com.rab3tech.customer.service;

import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;

public interface FundTransferService {

	void transfer(FundTransferVO fundTransferVO, LoginVO loginVO);

}
