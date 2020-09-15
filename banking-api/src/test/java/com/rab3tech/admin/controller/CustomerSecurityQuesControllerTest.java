package com.rab3tech.admin.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rab3tech.admin.service.CustomerSecurityQuestionsService;
import com.rab3tech.vo.ApplicationResponseVO;

@RunWith(MockitoJUnitRunner.class)
public class CustomerSecurityQuesControllerTest {
	private MockMvc mockMvc;
	@Mock
	private CustomerSecurityQuestionsService securityQuestionsService;
	@InjectMocks
	private CustomerSecurityQuesController custController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(custController).build();

	}

	@Test
	public void testUpdateSecurityQuestionStatus() throws Exception {
		ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
		applicationResponseVO.setCode(200);
		applicationResponseVO.setMessage("Customer security question status is updated");
		applicationResponseVO.setStatus("success");
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/security/uquestion").param("status", "test").param("qid", "5"))
				.andExpect(status().isOk());
		verify(securityQuestionsService, times(1)).updateStatus("test", 5);
		verifyNoMoreInteractions(securityQuestionsService);

	}

}
