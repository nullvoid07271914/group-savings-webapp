package com.groupsavings.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfitViewController {

    @GetMapping("/profits")
    public String viewMemberProfits(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());
        return "member_profits";
    }
}
