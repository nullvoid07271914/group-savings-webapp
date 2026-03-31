package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContributionViewController {

	@GetMapping("/contribution/add-contribute")
	public String viewBorrowerForLoan(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "add_contribute";
	}

	@GetMapping("/contribution/summary")
	public String viewMemberContributions(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "member_contributions";
	}
}
