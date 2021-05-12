package com.libraryapp.controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value="/")
	public String redirectToHome() {
		
		UserDetails principal = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	  
		Collection<? extends GrantedAuthority> role = new ArrayList<>();
		role = principal.getAuthorities();
		
		if (role.toString().equals("[ROLE_ADMIN]")){
			return "redirect:/admin";
		} else if (role.toString().equals("[ROLE_EMPLOYEE]")){
			return "redirect:/employee";
		} else {
			return "redirect:/user";
		}
	}
}
