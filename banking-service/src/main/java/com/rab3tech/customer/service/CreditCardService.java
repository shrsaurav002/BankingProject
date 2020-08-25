package com.rab3tech.customer.service;

import com.rab3tech.vo.CreditCardVO;

public interface CreditCardService {

	byte[] ccFront(String email);

	byte[] ccBack(String email);

	CreditCardVO createNewCreditCard(String email, int id);

	CreditCardVO findByEmail(String email);

	boolean checkIfExist(String email);

}
