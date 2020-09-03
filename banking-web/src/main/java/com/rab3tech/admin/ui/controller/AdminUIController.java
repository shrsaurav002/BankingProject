package com.rab3tech.admin.ui.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
//http:localhost:4055
//httt://www.kuebikobank.com/admin/security/questions
@Scope("singleton")
public class AdminUIController {

	@GetMapping("/dashboard")
	public String goToDashboard() {
		return "admin/dashboard";
	}

}
