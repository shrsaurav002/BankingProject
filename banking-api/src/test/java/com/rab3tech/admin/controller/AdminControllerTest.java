package com.rab3tech.admin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.test.TestUtil;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.RolesUpdateRequest;

public class AdminControllerTest {


	private MockMvc mockMvc;
	@Mock
	private CustomerService customerService;
	@InjectMocks
	private AdminController adminController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
	}

	@Test
	public void testDeleteCustWhenExist() throws Exception {
		Map<String, String> userDetails= new HashMap<>();
		userDetails.put("email", "shr.saurav@abc.com");
		userDetails.put("id", "5");
		when(customerService.deleteAccountCompletely("shr.saurav@abc.com", 5)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v4/admin/deleteCustomer").contentType(MediaType.APPLICATION_JSON)
	        .content(TestUtil.convertObjectToJsonBytes(userDetails))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.code").exists())
			.andExpect(jsonPath("$.message").exists())
			.andExpect(jsonPath("$.code").value("200"))
			.andExpect(jsonPath("$.message").value("Successfully Deleted"))
			.andDo(print());
		
		verify(customerService, times(1)).deleteAccountCompletely("shr.saurav@abc.com", 5);
        verifyNoMoreInteractions(customerService);
	}
	@Test
	public void testDeleteCustWhenNotExist() throws Exception {
		Map<String, String> userDetails= new HashMap<>();
		userDetails.put("email", "shr.saurav@abc.com");
		userDetails.put("id", "5");
		when(customerService.deleteAccountCompletely("shr.saurav@abc.com", 5)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.delete("/v4/admin/deleteCustomer").contentType(MediaType.APPLICATION_JSON)
	        .content(TestUtil.convertObjectToJsonBytes(userDetails))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.code").exists())
			.andExpect(jsonPath("$.message").exists())
			.andExpect(jsonPath("$.code").value("303"))
			.andExpect(jsonPath("$.message").value("Internal Error."))
			.andDo(print());
		
		verify(customerService, times(1)).deleteAccountCompletely("shr.saurav@abc.com", 5);
        verifyNoMoreInteractions(customerService);
	}
}
