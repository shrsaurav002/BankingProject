package com.rab3tech.admin.controller;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rab3tech.customer.service.LoginService;
import com.rab3tech.test.TestUtil;
import com.rab3tech.vo.RoleVO;
import com.rab3tech.vo.RoleVOWrapper;
import com.rab3tech.vo.RolesUpdateRequest;

@RunWith(MockitoJUnitRunner.class)
public class RoleControllerTest {
	private MockMvc mockMvc;
	@Mock
	private LoginService loginService;
	@InjectMocks
	private RoleController roleController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
	}

	@Ignore
	@Test
	public void testGetRoleWhenExist() {
		fail("Not yet implemented");
	}

	/*
	 * @GetMapping("/roles")
	 * 
	 * @ResponseStatus(HttpStatus.OK) public List<RoleVO> getRoles() { return
	 * loginService.findRoles(); }
	 * 
	 */

	@Test
	public void testGetRoles() throws Exception {
		RoleVO roleVO = new RoleVO();
		roleVO.setId(100);
		roleVO.setName("Admin");
		roleVO.setDescription("administrator");
		RoleVO roleVO1 = new RoleVO();
		roleVO1.setId(200);
		roleVO1.setName("Emp");
		roleVO1.setDescription("employee");
		List<RoleVO> roles = new ArrayList<>();
		roles.add(roleVO);
		roles.add(roleVO1);
		when(loginService.findRoles()).thenReturn(roles);
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/roles")).andExpect(status().isOk());
		
		verify(loginService, times(1)).findRoles();
		verifyNoMoreInteractions(loginService);
	}


	@Test
	public void testUpdateCustomerRoles() throws Exception{
		RolesUpdateRequest rolesUpdateRequest=new RolesUpdateRequest();
		rolesUpdateRequest.setCid(101);
		rolesUpdateRequest.setRolesid(Arrays.asList(100,200));
		
		 mockMvc.perform(MockMvcRequestBuilders.put("/v3/customer/roles")
		 	        .contentType(MediaType.APPLICATION_JSON)
		 	        .content(TestUtil.convertObjectToJsonBytes(rolesUpdateRequest))
		 			.accept(MediaType.APPLICATION_JSON))
		 			.andExpect(jsonPath("$.code").exists())
		 			.andExpect(jsonPath("$.status").exists())
		 			.andExpect(jsonPath("$.code").value("201"))
		 			.andExpect(jsonPath("$.status").value("success"))
		 			.andDo(print());
		 	
			verify(loginService, times(1)).updateCustomerRoles(any(RolesUpdateRequest.class));
	        verifyNoMoreInteractions(loginService);
	} 
	@GetMapping("/customer/roles")
	@ResponseStatus(HttpStatus.OK)
	public RoleVOWrapper getCustomerRoles(@RequestParam String userid) {
		List<RoleVO> customerRoles=loginService.findRolesByUserid(userid);
		List<RoleVO> roleVOs=loginService.findRoles();
		RoleVOWrapper roleVOWrapper=new RoleVOWrapper();
		
		List<Integer>  customersId=customerRoles.stream().map(r->r.getId()).collect(Collectors.toList());
		roleVOWrapper.setCurrentRoles(customersId);
		roleVOWrapper.setRoleVOs(roleVOs);
		return roleVOWrapper;
	}
	
	@Test
	public void testGetCustomerRoles() throws Exception{
		RoleVO roleVO = new RoleVO();
		roleVO.setId(100);
		roleVO.setName("Admin");
		roleVO.setDescription("administrator");
		RoleVO roleVO1 = new RoleVO();
		roleVO1.setId(200);
		roleVO1.setName("Emp");
		roleVO1.setDescription("employee");
		List<RoleVO> roles = new ArrayList<>();
		roles.add(roleVO);
		roles.add(roleVO1);
		List<RoleVO> customerRoles = new ArrayList<>();
		customerRoles.add(roleVO1);
		when(loginService.findRolesByUserid("shr.saurav002@gmail.com")).thenReturn(customerRoles);
		when(loginService.findRoles()).thenReturn(roles);
		mockMvc.perform(MockMvcRequestBuilders.get("/v3/customer/roles").param("userid", "shr.saurav002@gmail.com")).andExpect(status().isOk());
		verify(loginService, times(1)).findRoles();
		verify(loginService, times(1)).findRolesByUserid("shr.saurav002@gmail.com");
		verifyNoMoreInteractions(loginService);
	}

}
