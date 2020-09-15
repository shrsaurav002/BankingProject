package com.rab3tech.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.FundTransferRepo;
import com.rab3tech.customer.dao.repository.WireTransferRepo;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.FundTransferEntity;
import com.rab3tech.dao.entity.TransferStatus;
import com.rab3tech.dao.entity.WireTransferEntity;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.WireTransferVO;

@Service
@Transactional
public class FundTransferServiceImpl implements FundTransferService {
	@Autowired
	private CustomerAccountInfoRepository custAccInfoRepo;
	@Autowired
	private WireTransferRepo wireRepo;
	@Autowired
	private FundTransferRepo fundRepo;

	@Override
	public void transfer(FundTransferVO fundTransferVO, LoginVO loginVO) {
		String custId = loginVO.getUsername();
		CustomerAccountInfo customerFrom = custAccInfoRepo
				.findByCustomerUsernameAndAccountType(custId, fundTransferVO.getSentFrom()).get();
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
		fundTransferEntity.setTransactionDate(new Date());
		fundRepo.save(fundTransferEntity);
	}

	@Override
	public List<FundTransferVO> findTransactionByUser(String username) {
		Optional<List<FundTransferEntity>> optional = fundRepo.findBySender(username);
		if (optional.isPresent()) {
			List<FundTransferEntity> entities = optional.get();

			List<FundTransferVO> transferVO = new ArrayList<>();
			for (FundTransferEntity e : entities) {

				FundTransferVO fundVO = new FundTransferVO();
				if (e.getSentTo() != null) {
					fundVO.setSentTo(e.getSentTo().getAccountNumber() + " " + e.getSentTo().getCustomerId().getName());
				} else {
					fundVO.setSentTo("Deleted Account");
				}
				fundVO.setAmount(e.getAmount());
				fundVO.setRemarks(e.getRemarks());
				fundVO.setSentFrom(e.getSentFrom().getAccountType().getName());
				fundVO.setTransactionDate(e.getTransactionDate());
				transferVO.add(fundVO);
			}
			return transferVO;
		} else {
			return null;
		}
		/*
		 * List<FundTransferVO> transferVO = entities.stream().map(t -> { FundTransferVO
		 * fundTransfer = new FundTransferVO();
		 * fundTransfer.setSentTo(t.getSentTo().getAccountNumber() + " " +
		 * t.getSentTo().getCustomerId().getName());
		 * fundTransfer.setAmount(t.getAmount());
		 * fundTransfer.setRemarks(t.getRemarks());
		 * fundTransfer.setSentFrom(t.getSentFrom().getAccountType().getName());
		 * fundTransfer.setTransactionDate(t.getTransactionDate()); return fundTransfer;
		 * }).collect(Collectors.toList());
		 */
	}

	@Override
	public String convertToWords(String amount) {

		return Utils.amountToWords(amount);
	}

	@Override
	public void wireTransferInit(WireTransferVO wireTransferVO) {
		String confirm = Utils.generateConfirmationNumber();
		WireTransferEntity wireTransferEntity = new WireTransferEntity();
		BeanUtils.copyProperties(wireTransferVO, wireTransferEntity);
		wireTransferEntity.setConfirmCode(confirm);
		TransferStatus transferStatus = new TransferStatus();
		transferStatus.setId(1);
		wireTransferEntity.setTransferStatus(transferStatus);
		wireRepo.save(wireTransferEntity);
	}

}
