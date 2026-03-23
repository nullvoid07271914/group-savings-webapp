package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberViewController {

	@GetMapping("/member/register")
	public String viewMemberRegistration(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "register_member";
	}

	@GetMapping("/member/info/{memberCode}")
	public String viewMemberForLoan(@PathVariable String memberCode, Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "apply_loan";
	}

	@GetMapping("/member/list")
	public String viewMemberList(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "member_list";
	}
	
	@GetMapping("/member/contributions")
	public String viewMemberContributions(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "member_contributions";
	}
}
