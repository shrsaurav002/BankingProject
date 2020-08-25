package com.rab3tech.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CreditCardService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.CreditCardVO;

@RestController
@RequestMapping("/credit")
public class CreditCardController {
	@Autowired
	private CreditCardService creditService;
	@Autowired
	private EmailService emailS;

	@GetMapping("/customer/createCcData")
	public CreditCardVO generateCreditCard(@RequestParam String email, @RequestParam int id) {

		CreditCardVO credit = creditService.createNewCreditCard(email, id);
		return credit;
	}

	@GetMapping("/checkIfExitst")
	public boolean check(@RequestParam String email) {
		boolean check = creditService.checkIfExist(email);
		return check;
	}

	@PostMapping("/creditCard/sendEmail")
	public String sendcreditEmail(@RequestParam String email, @RequestParam String name) {
		CreditCardVO userCard = creditService.findByEmail(email);

		emailS.sendCreditCardEmail(email, name, creditService.ccFront(userCard.getEmail()),
				creditService.ccBack(email));
		return "success";
	}
}
