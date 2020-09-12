package com.rab3tech.customer.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rab3tech.customer.dao.repository.CustomerAccountInfoRepository;
import com.rab3tech.customer.dao.repository.FundTransferRepo;
import com.rab3tech.dao.entity.CustomerAccountInfo;
import com.rab3tech.dao.entity.FundTransferEntity;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;

public class FundTransferServiceImplTest {
	@Mock
	private CustomerAccountInfoRepository custAccInfoRepo;
	@Mock
	private FundTransferRepo fundRepo;
	@InjectMocks
	private FundTransferServiceImpl fundTransferService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testTransfer() {
		Login login = new Login();
		login.setEmail("test@abc.com");
		login.setLoginid("test@abc.com");
		login.setName("JUNIT");
		Login login2 = new Login();
		login2.setEmail("abc@abc.com");
		login2.setLoginid("abc@abc.com");
		login2.setName("hi");
		CustomerAccountInfo customerFrom = new CustomerAccountInfo();
		customerFrom.setAccountNumber("123451234567876543");
		customerFrom.setAvBalance(21f);
		customerFrom.setTavBalance(320f);
		customerFrom.setCustomerId(login);
		Optional<CustomerAccountInfo> optionalFrom = Optional.of(customerFrom);
		CustomerAccountInfo customerTo = new CustomerAccountInfo();
		customerTo.setAccountNumber("123219876");
		customerTo.setAvBalance(20f);
		customerTo.setTavBalance(32f);
		customerTo.setCustomerId(login2);
		Optional<CustomerAccountInfo> optionalTo = Optional.of(customerTo);
		FundTransferVO fundTransferVO = new FundTransferVO(1, "Checking", "1232198765432345678", 21f, "Test Op", 12345);
		LoginVO loginVO = new LoginVO();
		loginVO.setEmail("test@abc.com");
		loginVO.setName("JUNIT");
		loginVO.setUsername("test@abc.com");
		String toAccNo = fundTransferVO.getSentTo().substring(0, 9);
		when(custAccInfoRepo.findByAccountNumber(toAccNo)).thenReturn(optionalTo);
		when(custAccInfoRepo.findByCustomerUsernameAndAccountType(loginVO.getEmail(), fundTransferVO.getSentFrom()))
				.thenReturn(optionalFrom);
		fundTransferService.transfer(fundTransferVO, loginVO);
		verify(fundRepo, times(1)).save(any(FundTransferEntity.class));
	}


	@Test
	public void testFindTransactionByUserWhenExists() {
		List<FundTransferEntity> entities=new ArrayList<FundTransferEntity>();
		Login login = new Login();
		login.setEmail("shrsaurav@test.abc");
		login.setLoginid("shrsaurav@test.abc");
		login.setName("JUNIT");
		CustomerAccountInfo customerFrom = new CustomerAccountInfo();
		customerFrom.setAccountNumber("123451234567876543");
		customerFrom.setAvBalance(21f);
		customerFrom.setTavBalance(320f);
		customerFrom.setCustomerId(login);
		Login login2 = new Login();
		login2.setEmail("abc@abc.com");
		login2.setLoginid("abc@abc.com");
		login2.setName("hi");
		CustomerAccountInfo customerTo = new CustomerAccountInfo();
		customerTo.setAccountNumber("123219876");
		customerTo.setAvBalance(20f);
		customerTo.setTavBalance(32f);
		customerTo.setCustomerId(login2);
		FundTransferEntity entity=new FundTransferEntity();
		entity.setId(1);
		entity.setAmount(21f);
		entity.setSentFrom(customerFrom);
		entity.setSentTo(customerTo);
		entities.add(entity);
		Login login3 = new Login();
		login3.setEmail("abcded@abc.com");
		login3.setLoginid("abcded@abc.com");
		login3.setName("hihe");
		CustomerAccountInfo customerTo1 = new CustomerAccountInfo();
		customerTo.setAccountNumber("123219876");
		customerTo.setAvBalance(20f);
		customerTo.setTavBalance(32f);
		customerTo.setCustomerId(login3);
		FundTransferEntity entity2=new FundTransferEntity();
		entity.setId(2);
		entity.setAmount(30f);
		entity.setSentFrom(customerFrom);
		entity.setSentTo(customerTo1);
		entities.add(entity2);
		Optional<List<FundTransferEntity>> optional =Optional.of(entities);
		when(fundRepo.findBySender("shrsaurav@test.abc")).thenReturn(optional);
		List<FundTransferVO> transferVO=fundTransferService.findTransactionByUser("shrsaurav@test.abc");
		assertNotNull(transferVO);
		assertEquals(transferVO.size(), 2);
		assertEquals(transferVO.get(0).getId(), 1);
		verify(fundRepo,times(1)).findBySender("shrsaurav@test@abc.com");
		verifyNoMoreInteractions(fundRepo);
	}

	@Test(expected = NullPointerException.class)
	public void testFindTransactionByUserWhenNotExists() {
		Optional<List<FundTransferEntity>> list = Optional.empty();
		when(fundRepo.findBySender("shrsaurav@abc.com")).thenReturn(list);
		List<FundTransferVO> entity = fundTransferService.findTransactionByUser("shrsaurav@abc.com");
		assertEquals(0, entity.size());
	}

	@Test
	public void testConvertToWords() {
		String amount = "11";
		assertEquals(fundTransferService.convertToWords(amount).substring(0, 6), "Eleven");
	}

}
