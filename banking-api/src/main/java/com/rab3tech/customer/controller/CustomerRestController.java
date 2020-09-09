package com.rab3tech.customer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.dao.entity.CreditCardEntity;
import com.rab3tech.utils.PasswordGenerator;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.ChangePasswordRequestVO;
import com.rab3tech.vo.CreditCardVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginRequestVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerRestController {

	@Autowired
	private LoginService loginService;
	@Autowired
	private CustomerService customer;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private FundTransferService fundTransferService;

	// { "loginid":"nagen@gmail.com",
	// "passcode":"2938939",
	// "newpassword":"*(#$*$*$*$$&"
	//
	// } @RequestBody // it takes JSON data from request body and converts into Java
	// Object
	@PostMapping("/customer/change/password")
	public ApplicationResponseVO updateCustomerPassword(@RequestBody ChangePasswordRequestVO changePasswordRequestVO) {

		String status = loginService.updatePassword(changePasswordRequestVO);
		ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
		if ("success".equalsIgnoreCase(status)) {
			applicationResponseVO.setCode(200);
			applicationResponseVO.setStatus("success");
			applicationResponseVO.setMessage("Your password is updated successfully.");
			return applicationResponseVO;
		} else {
			applicationResponseVO.setCode(0);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage(status);
			return applicationResponseVO;
		}
	}

	@GetMapping("/customer/passcode")
	// var promise= fetch("v3/customer/passcode?usernameOrEmail="+usernameEmail);
	public ApplicationResponseVO sendPassCode(@RequestParam("usernameOrEmail") String usernameOrEmail) {
		String email = usernameOrEmail;

		String passCode = PasswordGenerator.generateRandomPassword(8);
		String result = loginService.updatePassCode(email, passCode);
		if ("notexist".equalsIgnoreCase(result)) {
			ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
			applicationResponseVO.setCode(0);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage("Sorry, this username or email does not exist , " + email);
			return applicationResponseVO;
		}
		try {
			// below line will send the email
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject("Regarding your passcode to change password");
			mailMessage.setText("Hey! " + result + " ,   your password code is  = " + passCode);
			mailMessage.setFrom("javahunk100@gmail.com");
			javaMailSender.send(mailMessage);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Your passcode is  = " + passCode);
		}
		ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
		applicationResponseVO.setCode(200);
		applicationResponseVO.setStatus("success");
		applicationResponseVO.setMessage("Passcode has been sent on your email " + email);
		return applicationResponseVO;
	}

	@PostMapping("/user/login")
	public ApplicationResponseVO authUser(@RequestBody LoginRequestVO loginRequestVO) {
		Optional<LoginVO> optional = loginService.findUserByUsername(loginRequestVO.getUsername());
		ApplicationResponseVO applicationResponseVO = new ApplicationResponseVO();
		if (optional.isPresent()) {
			applicationResponseVO.setCode(200);
			applicationResponseVO.setStatus("success");
			applicationResponseVO.setMessage("Userid is correct");
		} else {
			applicationResponseVO.setCode(400);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage("Userid is not correct");
		}
		return applicationResponseVO;
	}

	@GetMapping("/customer/searchCustomers")
	public List<CustomerVO> searchCustomers(@RequestParam String searchText) {
		List<CustomerVO> customers = new ArrayList<>();
		customers = customer.searchCustomers(searchText);
		return customers;

	}

	@GetMapping("/customer/findCustByAccNo")
	public String findCust(@RequestParam String accNo) {
		String cust = customer.findCustByAccountNum(accNo);
		return cust;
	}

	@GetMapping("/customer/findEmail")
	public CustomerVO findEmail(@RequestParam String email) {
		CustomerVO customerVO = customer.findByEmail(email);
		return customerVO;

	}

	@GetMapping("/customer/createCcData")
	public CreditCardVO generateCreditCard(@RequestParam String email, @RequestParam int id) {
		CreditCardVO creditcard = new CreditCardVO();
		CustomerVO customerVO = customer.findByEmail(email);
		creditcard.setCardNumber((long) (Math.random() * Math.pow(10, 16)));
		creditcard.setExpDate(new Date());
		creditcard.setName(customerVO.getName());
		double code = 0;
		while (code < 100 || code > 1000) {
			code = Math.random() * 1000;
		}
		creditcard.setSecCode((int) code);
		return creditcard;
	}

	@PostMapping("/customer/saveCreditCard")
	public String createCreditCard(@RequestParam Map<String, CreditCardVO> credit) {
		System.out.println(credit);
		CreditCardEntity ccEntity = new CreditCardEntity();
		Map<String, Object> ccMap = new HashMap<>();
		for (Entry<String, CreditCardVO> e : credit.entrySet()) {
			String key = e.getKey().substring(7, e.getKey().length() - 1);
			ccMap.put(key, e.getValue());
		}
		for (Entry<String, Object> e : ccMap.entrySet()) {
			System.out.println("key+ " + e.getKey() + " , " + e.getValue());
		}
		ccEntity.setApr(12.35);
		ccEntity.setCardNumber((long) ccMap.get("cardNumber"));
		ccEntity.setCashbackBonus(50.32);
		ccEntity.setCreditScore(700);
		ccEntity.setCurrentBalance(600d);
//		ccEntity.setExpDate( (Date) credit.get("expDate"));
//		ccEntity.setMinPayment(35d);
//		ccEntity.setName((String) credit.get("apr"));
//		ccEntity.setSecCode( (int) credit.get("apr"));
//		ccEntity.setStatementBalance( 1000d);
		System.out.println(ccEntity);
		return "success";
	}

	@GetMapping("/customer/findPayeeUrn")
	public String checkUrn(@RequestParam String payeeName, @RequestParam String customerId) {
		String urn = "";
		int urnVal = customer.findPayeeUrn(payeeName, customerId);
		return urn + urnVal;

	}

	@DeleteMapping("/customer/rejectPayee")
	public ApplicationResponseVO delete(@RequestParam String customerId, @RequestParam String name) {
		customer.updatePayee(customerId, name, "reject");
		ApplicationResponseVO appResp = new ApplicationResponseVO();
		appResp.setCode(917);
		appResp.setId(2);
		appResp.setMessage("Successfully Deleted");
		appResp.setStatus("Complete");
		appResp.setUserid(customerId);
		return appResp;
	}

	@GetMapping("/customer/checkAccNumber")
	public String checkAccNumber(@RequestParam String loginid) {
		String accNum = customer.getAccountNumber(loginid);
		return accNum;

	}

	@GetMapping("/customer/fetchAccounts")
	public List<String> fetchUserAccounts(@RequestParam String loginid) {
		List<String> accountTypes = customer.findAccountTypesByUsername(loginid);
		return accountTypes;

	}

	@GetMapping("/customer/fetchYourRegistered")
	public List<PayeeInfoVO> fetchRegisteredAccounts(@RequestParam String loginid) {
		List<PayeeInfoVO> accounts = customer.registeredPayeeList(loginid);
		return accounts;
	}

	@GetMapping("/customer/getAccountBalance")
	public float fetchUserBalance(@RequestParam String loginid, String accountType) {
		float amount = customer.findAccountBalance(loginid, accountType);
		return amount;
	}

	@GetMapping("/customer/generateNewOTP")
	public int genNewOTP() {
		int n = Utils.genOTP();
		return n;
	}

	@GetMapping("/customer/transactionHistory")
	public List<FundTransferVO> statement(@RequestParam String loginid) {
		List<FundTransferVO> fundTransfers = fundTransferService.findTransactionByUser(loginid);
		System.out.println(fundTransfers);
		return fundTransfers;
	}

	@GetMapping("/customer/convertToWords")
	public String convertToWords(@RequestParam String amount) {
		String money = fundTransferService.convertToWords(amount);
		return money;

	}
}
