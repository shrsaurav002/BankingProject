package com.rab3tech.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerEnquiryService;
import com.rab3tech.vo.CustomerSavingVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerStatus {

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	@GetMapping("/customer/app/status")
	public CustomerSavingVO AppStatus(@RequestParam String searchText) {
//		CustomerSavingVO customer = new CustomerSavingVO();
//		customer.setAccType("Saving");
//		customer.setAppref("123");
//		customer.setCsaid(213);
//		customer.setDoa(new Date());
//		customer.setEmail("shrs@abc.com");
//		customer.setLocation("here");
//		customer.setMobile("132456");
//		customer.setName("Saurav");
		CustomerSavingVO customer = customerEnquiryService.findAppStatus(searchText);
		return customer;
	}
}
