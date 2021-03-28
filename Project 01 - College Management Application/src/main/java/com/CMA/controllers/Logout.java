package com.CMA.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.CMA.session.SessionHandler;

@Controller
public class Logout {
	
	@GetMapping(value="/logout")
	public String logout() {
		SessionHandler.getSession().setLoggedIn(false);
		SessionHandler.getSession().setUserId(-1);
		SessionHandler.getSession().setUserType("");
		return "redirect:/";
	}
	
}
