package com.rab3tech.customer.ui.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.customer.service.CreditCardService;

@Controller
public class CreditController {
	@Autowired
	private CreditCardService creditService;

	@GetMapping("/load/image/CcFront")
	public void findCcFront(@RequestParam String email, HttpServletResponse response) throws IOException {

		// create util to generate cc number, expdate, and store in CreditCardVO. call
		// the following, and store when you press submit
		response.setContentType("image/png");

		byte[] photo = creditService.ccFront(email);
		ServletOutputStream outputStream = response.getOutputStream();
		if (photo != null && photo.length > 0) {
			outputStream.write(photo);
			outputStream.flush();
		}
		outputStream.close();
	}

	@GetMapping("/load/image/CcBack")
	public void findCcBack(@RequestParam String email, HttpServletResponse response) throws IOException {

		response.setContentType("image/png");

		byte[] photo = creditService.ccBack(email);
		ServletOutputStream outputStream = response.getOutputStream();
		if (photo != null && photo.length > 0) {
			outputStream.write(photo);
			outputStream.flush();
		}
		outputStream.close();
	}


}
