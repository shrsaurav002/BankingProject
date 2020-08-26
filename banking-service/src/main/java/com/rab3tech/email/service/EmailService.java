package com.rab3tech.email.service;

import javax.validation.Valid;

import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.PayeeInfoVO;

public interface EmailService {

	String sendEquiryEmail(EmailVO mail)  ;

	String sendRegistrationEmail(EmailVO mail);

	String sendUsernamePasswordEmail(EmailVO mail);

	void sendDenialEmail(EmailVO email);


	void sendCreditCardEmail(String email, String name,byte[] creditFront, byte[] creditBack);

	void sendUrnEmail(@Valid PayeeInfoVO payeeInfoVO, int urn);

}
