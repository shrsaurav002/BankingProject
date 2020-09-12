package com.rab3tech.admin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rab3tech.admin.dao.repository.CustomerSecurityQuestionsRepository;
import com.rab3tech.dao.entity.SecurityQuestions;
import com.rab3tech.vo.SecurityQuestionsVO;

public class CustomerSecurityQuestionsServiceImplTest {
	@Mock
	private CustomerSecurityQuestionsRepository customerSecurityQuestionsDao;
	@InjectMocks
	private CustomerSecurityQuestionsServiceImpl customerSecurityQuestions;

	@Before
	public void start() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUpdateStatus() {
		SecurityQuestions secQ = new SecurityQuestions();
		secQ.setOwner("shrsaurav@test.com");
		secQ.setQid(100);
		secQ.setQuestions("Whats the name?");
		secQ.setStatus("yes");
		Optional<SecurityQuestions> optional = Optional.of(secQ);
		when(customerSecurityQuestionsDao.findById(100)).thenReturn(optional);
		customerSecurityQuestions.updateStatus("yes", 100);
		verify(customerSecurityQuestionsDao, times(1)).findById(100);
		verifyNoMoreInteractions(customerSecurityQuestionsDao);
	}

	@Test(expected = NoSuchElementException.class)
	public void testUpdateStatusIfNotExists() {
		Optional<SecurityQuestions> optional = Optional.empty();
		when(customerSecurityQuestionsDao.findById(100)).thenReturn(optional);
		customerSecurityQuestions.updateStatus("yes", 100);
	}

	@Test
	public void testFindSecurityQuestionsWhenExists() {
		List<SecurityQuestions> sec = new ArrayList<>();
		SecurityQuestions sqv = new SecurityQuestions();
		sqv.setOwner("Saurav@abc.com");
		sqv.setQid(100);
		sqv.setQuestions("Name?");
		sqv.setStatus("yes");
		SecurityQuestions sqv1 = new SecurityQuestions();
		sqv1.setOwner("Saurav@abc.com");
		sqv1.setQid(101);
		sqv1.setQuestions("Class?");
		sqv1.setStatus("yes");

		sec.add(sqv);
		sec.add(sqv1);

		when(customerSecurityQuestionsDao.findAll()).thenReturn(sec);
		List<SecurityQuestionsVO> questionsVOs = customerSecurityQuestions.findSecurityQuestions();
		assertNotNull(questionsVOs);
		assertEquals(2, questionsVOs.size());
		assertEquals(questionsVOs.get(0).getQuestions(), "Name?");
		verify(customerSecurityQuestionsDao, times(1)).findAll();
		verifyNoMoreInteractions(customerSecurityQuestionsDao);
	}

	@Test
	public void testFindSecurityQuestionsWhenNotExists() {
		List<SecurityQuestions> sec = new ArrayList<>();
		when(customerSecurityQuestionsDao.findAll()).thenReturn(sec);
		List<SecurityQuestionsVO> questionsVOs = customerSecurityQuestions.findSecurityQuestions();
		assertNotNull(questionsVOs);
		assertEquals(0, questionsVOs.size());
		verify(customerSecurityQuestionsDao, times(1)).findAll();
		verifyNoMoreInteractions(customerSecurityQuestionsDao);
	}

	@Test
	public void testAddSecurityQuestion() {
		customerSecurityQuestions.addSecurityQuestion("Whats your name?", "saurav@abc.com");
		verify(customerSecurityQuestionsDao, times(1)).save(any(SecurityQuestions.class));
	}

}
