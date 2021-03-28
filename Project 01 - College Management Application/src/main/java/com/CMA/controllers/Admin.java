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
import com.CMA.DAO.GradeRepository;
import com.CMA.DAO.StudentRepository;
import com.CMA.DAO.TeacherRepository;
import com.CMA.entities.AdminEntity;
import com.CMA.entities.GradeEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.entities.TeacherEntity;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/admin")
public class Admin {

private String portalType="ADMIN";	

@Autowired
AdminRepository adRepo;

@Autowired
StudentRepository studRepo;

@Autowired
TeacherRepository teachRepo;

@Autowired
GradeRepository gradRepo;

	@GetMapping
	public String mainPage(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		AdminEntity currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("user", currentAdmin);
		
		List<StudentEntity> students = (List<StudentEntity>) studRepo.findAll();
		int amountOfStudents = students.size();
		model.addAttribute("studentAmount", amountOfStudents);
		
		List<TeacherEntity> teachers = (List<TeacherEntity>) teachRepo.findAll(); 
		int amountOfTeachers = teachers.size();
		model.addAttribute("teacherAmount", amountOfTeachers);
		
		List<GradeEntity> grades = (List<GradeEntity>) gradRepo.findAll();
		double totalGrade = 0;
		double count = 0;
		for (GradeEntity grade : grades) {
			if(grade.getGrade() > 0.0) {
			totalGrade += grade.getGrade();
			count += 1;
			}
		}
		double averageGrade = totalGrade / count;
		model.addAttribute("averageGrade", averageGrade);
		
		return "admin-portal/admin-page-main.html";
	}	
	
	@GetMapping(value="/ManageAccount")
	public String manageAccount(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		AdminEntity currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("admin", currentAdmin);
		return "admin-portal/admin-manage-account.html";
	}
	
	@PostMapping(value="/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();	
		AdminEntity currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		if ( (!currentAdmin.getPassword().equals(oldPassword)) || (!newPassword.equals(newPassword2)) ){
			return "admin-portal/admin-change-password-failed.html";
		} else {
			currentAdmin.setPassword(newPassword);
			adRepo.save(currentAdmin);
			return "admin-portal/admin-change-password-succeeded.html";
		}
	}
}	

