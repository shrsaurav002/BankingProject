package com.rab3tech.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rab3tech.customer.service.CustomerEnquiryService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.EmailVO;

public class EmployeeControllerTest {
	private MockMvc mockMvc;
	@Mock
	private CustomerEnquiryService customerEnquiryService;
	@Mock
	private EmailService emailService;
	@InjectMocks
	private EmployeeController employeeController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void testCustomerEnquiryApprove() throws Exception {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO(12345, "Saurav", "shr.sauurav@abc.com", "135698", "VA",
				"Savings", "APPROVED", "testUcrid", new Date(), "123456789");
		EmailVO mail = new EmailVO(customerSavingVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerSavingVO.getName() + "  Account registration", "",
				customerSavingVO.getName());
		when(customerEnquiryService.changeEnquiryStatus(12345, "APPROVED")).thenReturn(customerSavingVO);
		mockMvc.perform(MockMvcRequestBuilders.post("/v3/customers/enquiry/approve").param("csaid", "12345"))
				.andExpect(status().isOk()).andDo(print());

		verify(customerEnquiryService, times(1)).changeEnquiryStatus(12345, "APPROVED");
		verify(customerEnquiryService, times(1)).updateEnquiryRegId(any(Integer.class), any(String.class));
		verify(emailService, times(1)).sendRegistrationEmail(mail);
		verifyNoMoreInteractions(customerEnquiryService);

	}

	@Test
	public void testCustomerEnquiryDeny() throws Exception {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO(12345, "Saurav", "shr.sauurav@abc.com", "135698", "VA",
				"Savings", "APPROVED", "testUcrid", new Date(), "123456789");

		when(customerEnquiryService.deleteEnquiry(12345)).thenReturn(1);
		when(customerEnquiryService.findById(12345)).thenReturn(customerSavingVO);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v3/customers/enquiry/deny").param("csaid", "12345")
				.param("reason", "tooMany").param("other", "")).andDo(print()).andReturn();
		verify(emailService, times(1)).sendDenialEmail(any(EmailVO.class));
		verify(customerEnquiryService, times(1)).findById(12345);
		verify(customerEnquiryService, times(1)).deleteEnquiry(12345);
		verifyNoMoreInteractions(customerEnquiryService);
	}

}
