package com.rab3tech.admin.ui.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rab3tech.customer.service.CustomerEnquiryService;
import com.rab3tech.vo.CustomerSavingVO;

@Controller
@RequestMapping("/admin")
//http:localhost:4055
//httt://www.kuebikobank.com/admin/security/questions
@Scope("singleton")
public class AdminUIController {
	private static final Logger logger = LoggerFactory.getLogger(AdminUIController.class);
	@Autowired 
	private CustomerEnquiryService customerEnquiryService;
	
	@GetMapping("/dashboard")
	public String goToDashboard() {
		return "admin/dashboard";
	}

	@GetMapping(value= {"/enquiries"})
    @PreAuthorize("hasAuthority('ADMIN')")
	public String showCustomerEnquiry(Model model) {
		logger.info("showCustomerEnquiry is called!!!");
		List<CustomerSavingVO> pendingApplications = customerEnquiryService.findPendingEnquiry();
		model.addAttribute("applicants", pendingApplications);
		return "admin/customerEnquiryList";	//login.html
	}
	
}
