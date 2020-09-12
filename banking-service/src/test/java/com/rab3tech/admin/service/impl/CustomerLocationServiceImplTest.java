package com.rab3tech.admin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rab3tech.customer.dao.repository.CustomerLocationRepository;
import com.rab3tech.dao.entity.Location;
import com.rab3tech.dao.entity.Login;
import com.rab3tech.vo.LocationVO;

public class CustomerLocationServiceImplTest {
	@Mock
	private CustomerLocationRepository customerLocationRepository;

	@InjectMocks
	private CustomerLocationServiceImpl customerLocationServiceImpl;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindLocationWhenItExist() {
		List<Location> locations = new ArrayList<Location>();
		
		
		Location location = new Location();
		location.setId(1);
		location.setLcode("L410");
		Login login = new Login();
		login.setEmail("shrsaurav@gmail.com");
		login.setLoginid("shrsaurav@gmail.com");
		login.setName("Test");
		login.setNoOfAttempt(3);
		login.setPassword("testPass");
		location.setLogin(login);
		
		locations.add(location);
		
		
		when(customerLocationRepository.findAll()).thenReturn(locations);
		
		List<LocationVO> locationVOs = customerLocationServiceImpl.findLocation();
		assertNotNull(locationVOs);
		assertEquals(locationVOs.size(), 1);
		assertEquals(locationVOs.get(0).getLogin().getName(), "Test");
		verify(customerLocationRepository, times(1)).findAll();
		verifyNoMoreInteractions(customerLocationRepository);

	}

}
