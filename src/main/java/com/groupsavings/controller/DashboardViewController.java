package com.groupsavings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardViewController {

	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpServletRequest request) {
		model.addAttribute("currentUri", request.getRequestURI());
		return "dashboard";
	}
}
