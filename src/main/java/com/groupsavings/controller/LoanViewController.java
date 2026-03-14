package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoanViewController {

	@GetMapping("/loan/apply")
	public String viewBorrowerForLoan(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "search_borrower_form";
	}

	@GetMapping("/loan/manage")
	public String viewLoanManage(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "manage_loan";
	}
}
