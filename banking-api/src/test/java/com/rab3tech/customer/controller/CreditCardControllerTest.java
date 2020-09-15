package com.rab3tech.customer.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rab3tech.customer.service.CreditCardService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.CreditCardVO;

public class CreditCardControllerTest {
	private MockMvc mockMvc;
	@Mock
	private CreditCardService creditCardService;
	@Mock
	private EmailService emailService;
	@InjectMocks
	private CreditCardController creditController;

	@Before
	public void inti() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(creditController).build();
	}

	@Test
	public void testGenerateCreditCard() throws Exception {
		CreditCardVO credit = new CreditCardVO();
		credit.setCardNumber(12345678L);
		credit.setSecCode(256);
		credit.setEmail("shr.saurav@abc.com");
		when(creditCardService.createNewCreditCard("shr.saurav@abc.com", 5)).thenReturn(credit);
		mockMvc.perform(MockMvcRequestBuilders.get("/credit/customer/createCcData").param("email", "shr.saurav@abc.com")
				.param("id", "5")).andExpect(status().isOk()).andExpect(jsonPath("$.cardNumber").exists())
				.andExpect(jsonPath("$.secCode").exists()).andExpect(jsonPath("$.cardNumber").value(12345678L))
				.andExpect(jsonPath("$.secCode").value(256)).andDo(print());
		verify(creditCardService, times(1)).createNewCreditCard("shr.saurav@abc.com", 5);
		verifyNoMoreInteractions(creditCardService);
	}

	@Test
	public void testCheckIfExist() throws Exception {
		boolean test = false;
		when(creditCardService.checkIfExist("shr.saurav@abc.com")).thenReturn(test);
		mockMvc.perform(MockMvcRequestBuilders.get("/credit/checkIfExitst").param("email", "shr.saurav@abc.com"))
				.andExpect(status().isOk());
		verify(creditCardService, times(1)).checkIfExist("shr.saurav@abc.com");
		verifyNoMoreInteractions(creditCardService);
	}

	@Test
	public void testSendcreditEmail() throws Exception {
		CreditCardVO credit = new CreditCardVO();
		credit.setCardNumber(12345678L);
		credit.setSecCode(256);
		credit.setEmail("shr.saurav@abc.com");

		when(creditCardService.findByEmail("shr.saurav@abc.com")).thenReturn(credit);
		mockMvc.perform(MockMvcRequestBuilders.post("/credit/creditCard/sendEmail").param("email", "shr.saurav@abc.com")
				.param("name", "Saurav")).andExpect(status().isOk());

		verify(emailService, times(1)).sendCreditCardEmail("shr.saurav@abc.com", "Saurav", null, null);
		verify(creditCardService, times(1)).findByEmail("shr.saurav@abc.com");
		verify(creditCardService, times(1)).ccFront("shr.saurav@abc.com");
		verify(creditCardService, times(1)).ccBack("shr.saurav@abc.com");
		verifyNoMoreInteractions(emailService);
		verifyNoMoreInteractions(creditCardService);
	}

}
