package com.rab3tech.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rab3tech.admin.dao.repository.AccountTypeRepository;
import com.rab3tech.customer.service.AccountTypeService;
@Service
public class AccountTypeServiceImp implements AccountTypeService {
	@Autowired
	private AccountTypeRepository accType;

	@Override
	public List<String> AllAccountTypes() {
		return accType.findAllAccountTypes();
	}

}
