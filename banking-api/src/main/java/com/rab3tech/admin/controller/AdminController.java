package com.rab3tech.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.vo.ApplicationResponseVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v4")
public class AdminController {

	@Autowired
	private CustomerService customerService;

	@DeleteMapping("/admin/deleteCustomer")
	public ApplicationResponseVO deleteCust(@RequestBody Map<String, String> userDetails) {
		String email = userDetails.get("email");
		int id = Integer.parseInt(userDetails.get("id"));
		ApplicationResponseVO resp = new ApplicationResponseVO();
		boolean test=customerService.deleteAccountCompletely(email, id);
		if (test) {
			resp.setCode(200);
			resp.setMessage("Successfully Deleted");
		} else {
			resp.setCode(303);
			resp.setMessage("Internal Error.");
		}
		return resp;
	}
}
