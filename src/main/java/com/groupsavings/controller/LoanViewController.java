package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoanViewController {

	@GetMapping("/loan/apply")
	public String viewBorrowerForLoan(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "apply_loan";
	}

	@GetMapping("/loan/manage")
	public String viewLoanManage(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "manage_loan";
	}

	@GetMapping("/loan/manage/{memberCode}/{loanCode}")
	public String viewLoan(@PathVariable String memberCode, @PathVariable String loanCode, Model model,
			HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "manage_single_loan";
	}
}
