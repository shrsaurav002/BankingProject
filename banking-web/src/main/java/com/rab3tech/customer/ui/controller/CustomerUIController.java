package com.rab3tech.customer.ui.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.customer.service.AccountTypeService;
import com.rab3tech.customer.service.CustomerAccountInfoService;
import com.rab3tech.customer.service.CustomerEnquiryService;
import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.FundTransferService;
import com.rab3tech.customer.service.LocationService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.utils.Utils;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;
import com.rab3tech.vo.RemittanceVO;
import com.rab3tech.vo.WireTransferVO;

/**
 * 
 * @author nagendra This class for customer GUI
 *
 */
@Controller
//@RequestMapping("/customer")
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@Autowired
	private AccountTypeService accTypeService;
	@Autowired
	private SecurityQuestionService securityQuestionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private LocationService locationService;
	@Autowired
	private CustomerAccountInfoService customerAccountSerice;
	@Autowired
	private FundTransferService fundTransferService;

	@GetMapping("/customer/forget/password")
	public String forgetPassword() {
		// spring.thymeleaf.prefix=classpath:/src/main/resources/templates/
		return "customer/forgetPassword"; // forgetPassword.html
	}

	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName = "customer/dashboard";
		boolean status = loginService.checkPasswordValid(loginid, changePasswordVO.getCurrentPassword());
		if (status) {
			if (changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				viewName = "customer/dashboard";
				loginService.changePassword(changePasswordVO);
			} else {
				model.addAttribute("error", "Sorry , your new password and confirm passwords are not same!");
				return "customer/login"; // login.html
			}
		} else {
			model.addAttribute("error", "Sorry , your username and password are not valid!");
			return "customer/login"; // login.html
		}
		return viewName;
	}

	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(
			@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,
			HttpSession session) {
		LoginVO loginVO2 = (LoginVO) session.getAttribute("userSessionVO");
		String loginid = loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to thyme
			// leaf!!!!!

			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		// Write code to send email

		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO);
		customerAccountSerice.createNewAccount(customerVO);
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}

	/*
	 * @GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome"
	 * }) public String showCustomerEnquiryPage(Model model) { CustomerSavingVO
	 * customerSavingVO = new CustomerSavingVO(); // model is map which is used to
	 * carry object from controller to view model.addAttribute("customerSavingVO",
	 * customerSavingVO); return "customer/customerEnquiry"; // customerEnquiry.html
	 * }
	 */

	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		// LoadLocationAndAccountVO loadLocationAndAccountVOs = new
		// LoadLocationAndAccountVO();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		List<String> accountType = accTypeService.AllAccountTypes();
		// List<String> accType = customerEnquiryService.findAllAccountType();
		List<String> locations = locationService.AllLocations();
		model.addAttribute("location", locations);
		model.addAttribute("accType", accountType);
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}

	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@ModelAttribute CustomerSavingVO customerSavingVO, Model model) {
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}

	@GetMapping("/customer/app/status")
	public String customerAppStatus() {

		return "customer/appstatus";
	}

	@GetMapping("/customer/customerSearch")
	public String customerSearch() {

		return "customer/customerSearch";
	}

	@GetMapping("/customer/addPayee")
	public String customerAddPayee(Model model) {
		PayeeInfoVO payeeInfoVO = new PayeeInfoVO();
		model.addAttribute("payeeInfoVO", payeeInfoVO);
		return "customer/addPayee";
	}

	@PostMapping("/customer/account/confirmPayee")
	public String confirmPayee(@RequestParam("urnNumber") int urn, @RequestParam("customerId") String username,
			@RequestParam("payeeNameIn") String name, @RequestParam("buttonValue") String submitButton, Model model) {
		customerService.updatePayee(username, name, submitButton);
		if (submitButton.equalsIgnoreCase("accept")) {
			model.addAttribute("successMessage", "Successfully Confirmed");
		} else {
			model.addAttribute("successMessage", "Successfully Rejected");
		}
		return "customer/pendingPayee";
	}

	@PostMapping("/customer/account/approvePayee")
	public String approvePayee(@RequestParam String customerId, @RequestParam String name) {
		customerService.updatePayee(customerId, name, "accept");
		return "customer/pendingPayee";
	}

	@PostMapping("/customer/account/addPayee")
	public String newPayee(@Valid @ModelAttribute PayeeInfoVO payeeInfoVO, BindingResult result,
			@RequestParam String payeeEmail, HttpSession session, Model model) {
		LoginVO login = (LoginVO) session.getAttribute("userSessionVO");
		String custID = login.getUsername();
		payeeInfoVO.setCustomerId(custID);
		if (!payeeInfoVO.getAccNumberConfirm().equals(payeeInfoVO.getPayeeAccountNo())) {
			result.rejectValue("accNumberConfirm", "acc.number.notEqual", "The accont numbers does not match");
			;
		}
		boolean test = customerService.checkIfExists(payeeInfoVO.getAccNumberConfirm());
		if (!test) {
			result.rejectValue("payeeAccountNo", "acc.number.notPresent",
					"The accont numbers does not exists. Please check the number");
			;
		}
		boolean check = customerService.checkIfPayeeExists(payeeInfoVO.getPayeeAccountNo(), custID);
		if (check) {
			result.rejectValue("payeeAccountNo", "acc.number.exists.for.you",
					"You already have this account as your payee Or have rejected. Contact the bank!");
		}
		if (payeeEmail.length() == 0
				|| customerService.checkEmailByNumber(payeeEmail, payeeInfoVO.getPayeeAccountNo())) {
			result.rejectValue("payeeAccountNo", "acc.email.empty", "Please Enter Valid Email");
			;
		}
		if (result.hasErrors()) {
			model.addAttribute("payeeDetail", payeeInfoVO);
			return "customer/addPayee";
		} else {
			System.out.println(
					"MY CUSTOMER USERID =========================================" + payeeInfoVO.getCustomerId());
			// String loginId = loginService.findUserByName(payeeInfoVO.getPayeeName());
			// payeeInfoVO.setCustomerId(loginId);
			int urn = customerService.addPayee(payeeInfoVO, payeeEmail);
			emailService.sendUrnEmail(payeeInfoVO, urn);
			model.addAttribute("successMessage", "Payee added successfully");
			model.addAttribute("payeeDetail", payeeInfoVO);
			return "customer/payeeConfirm";
		}
	}

	@GetMapping("/customer/pendingPayee")
	public String pendinPayeeList(Model model, HttpSession session) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String email = loginVO.getUsername();
		List<PayeeInfoVO> payeeInfoList = customerService.pendingPayeeList(email);
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/pendingPayee";

	}

	@GetMapping("/customer/registeredPayee")
	public String registeredPayeeList(HttpSession session, Model model) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		String customerEmail = loginVO.getUsername();
		List<PayeeInfoVO> payeeInfoList = customerService.registeredPayeeList(customerEmail);
		List<String> accountTypes = customerService.findAccountTypesByUsername(loginVO.getUsername());
		model.addAttribute("accountType", accountTypes);
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/registeredPayee";

	}

//////////////////////////////////////////////////////////////////
	@GetMapping("/customer/depositFunds")
	public String openDepositPage(Model model) {
		CustomerAccountInfoVO cust = new CustomerAccountInfoVO();
		model.addAttribute("customerAccountInfoVO", cust);
		return "customer/depositFunds";
	}

	@PostMapping("/customer/depositFunds")
	public String depositMoney(@RequestParam String accountNumber, @RequestParam float depositAmount,
			@RequestParam String date, Model model) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		try {
			date1 = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		customerService.depositMoney(accountNumber, depositAmount, date1);
		model.addAttribute("success", "Successfully Deposited $" + depositAmount);
		return "customer/depositFunds";
	}

////////////////////////////////////////////////////////////////////
	@GetMapping("/load/image")
	public void findPhoto(@RequestParam String email, HttpServletResponse response) throws IOException {

		response.setContentType("image/png");

		byte[] photo = customerService.imageSearch(email);
		ServletOutputStream outputStream = response.getOutputStream();
		if (photo != null && photo.length > 0) {
			outputStream.write(photo);
			outputStream.flush();
		}
		outputStream.close();
	}

	// Transfer Money:
	@GetMapping("/customer/transferMoney")
	public String showFirstTransferPage(Model model) {
		/*
		 * LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		 * List<String>
		 * accountTypes=customerService.findAccountTypesByUsername(loginVO);
		 * model.addAttribute("accountType",accountTypes);
		 */
		FundTransferVO fundTransferVO = new FundTransferVO();
		model.addAttribute("fundTransferVO", fundTransferVO);
		return "customer/transferMoney";
	}

	@PostMapping("/customer/transferMoney")
	public String showConfirmationPage(@ModelAttribute FundTransferVO fundTransferVO, Model model) {

		int n = Utils.genOTP();
		fundTransferVO.setOtp(n);
		System.out.println(fundTransferVO);
		model.addAttribute("fundTransferVO", fundTransferVO);
		return "customer/transferConfirm";
	}

	@PostMapping("/customer/sendMoneyToPayee")
	public String sendMoneyToPayee(@ModelAttribute FundTransferVO fundTransferVO, @Valid @RequestParam int otpEnter,
			BindingResult br, Model model, HttpSession session) {
		System.out.println(fundTransferVO);
		if (fundTransferVO.getOtp() != otpEnter) {
			br.rejectValue("otp", "otp Does Not Match", "The entered OTP does not match. Check your email");
		}
		if (br.hasErrors()) {
			model.addAttribute("fundTransferVO", fundTransferVO);
			return "customer/transferConfirm";
		}
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		fundTransferService.transfer(fundTransferVO, loginVO);
		model.addAttribute("successMessage", "You have succesfully transferred $" + fundTransferVO.getAmount());
		return "customer/dashboard";
	}

	@GetMapping("/customer/wireTransfer")
	public String sendLargeAmount(Model model) {
		WireTransferVO wireTransferVO = new WireTransferVO();
		List<String> locations = locationService.AllLocations();
		model.addAttribute("location", locations);
		model.addAttribute("wireTransfer", wireTransferVO);
		return "customer/wireTransfer";
	}

	@PostMapping("/customer/wireTransfer")
	public String sendMoneyWire(@ModelAttribute WireTransferVO wireTransferVO, Model model) {
		fundTransferService.wireTransferInit(wireTransferVO);
		model.addAttribute("message", "Your request is processing. You will get an email with confirmation code soon.");
		return "customer/login";

	}

	@GetMapping("/customer/remittance")
	public String sendSmallAmount(Model model) {
		RemittanceVO remittance = new RemittanceVO();
		List<String> locations = locationService.AllLocations();
		model.addAttribute("location", locations);
		model.addAttribute("remittance", remittance);
		return "customer/remittance";
	}

	@PostMapping("/customer/remitConfirm")
	public String remitConfirm(@ModelAttribute RemittanceVO remittanceVO, Model model, HttpSession session) {
		LoginVO loginVO = (LoginVO) session.getAttribute("userSessionVO");
		fundTransferService.remittanceService(remittanceVO, loginVO.getUsername());

		System.out.println(remittanceVO);
		return "customer/dashboard";
	}
}
