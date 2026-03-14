package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContributionController {

	@GetMapping("/contribution/search-member")
	public String viewBorrowerForLoan(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "contribution/search_member";
	}

	@GetMapping("/contribution/add/{memberCode}")
	public String viewMemberRegistration(@PathVariable String memberCode, Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "contribution/member_contribution";
	}
}
