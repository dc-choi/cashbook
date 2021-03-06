package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping({"/index", "/"})
	public String index(HttpSession session) {
		if(session.getAttribute("LM") != null) {
			return "redirect:/home";
		} else if (session.getAttribute("admin") != null) {
			return "redirect:/admin";
		}
		return "index";
	}
	@GetMapping("/home")
	public String home(HttpSession session) {
		if(session.getAttribute("LM") == null) {
			return "redirect:/login";
		}
		return "home";
	}
	@GetMapping("/function")
	public String function() {
		return "function";
	}
}
