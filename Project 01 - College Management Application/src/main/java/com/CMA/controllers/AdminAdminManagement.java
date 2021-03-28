package com.CMA.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CMA.DAO.AdminRepository;
import com.CMA.entities.AdminEntity;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/admin")
public class AdminAdminManagement {

	private String portalType="ADMIN";	
	
	@Autowired
	AdminRepository adRepo;
	
	@GetMapping(value="/ManageAdmins")
	public String manageAdmins(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("admin", new AdminEntity());
		List<AdminEntity> allAdmins = (List<AdminEntity>) adRepo.findAll();
		model.addAttribute("allAdmins", allAdmins);
		return "admin-portal/admin-manage-admins.html";
	}
	
	@PostMapping(value="/saveAdmin")
	public String saveAdmin(AdminEntity admin, @RequestParam String password) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		admin.setPassword(password);
		adRepo.save(admin);
		return "redirect:/admin/adminSaved";
	}
	
	@GetMapping(value="/adminSaved")
	public String adminSaved() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-admin-saved.html";
	}
}
