package com.CMA.controllers;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CMA.DAO.AdminRepository;
import com.CMA.DAO.StudentRepository;
import com.CMA.DAO.TeacherRepository;
import com.CMA.entities.AdminEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.entities.TeacherEntity;
import com.CMA.session.Session;
import com.CMA.session.SessionHandler;
 

@Controller
public class Login {

@Autowired	
AdminRepository adRepo;

@Autowired
TeacherRepository teaRepo;

@Autowired
StudentRepository studRepo;
	
	
	@GetMapping
	public String login() {
		return "login/LoginScreen";
	}
	
	@GetMapping(value="/pleasetryagain")
	public String tryAgain() {
		return "login/LoginScreenUserNotFound";
	}
	
	@PostMapping(value="/authorisation")
	public String authorisation(@RequestParam String email, @RequestParam String password) {
		
		for (StudentEntity student : studRepo.findAll()) {
			if (student.getEmail().toLowerCase().equals(email.toLowerCase()) && student.getPassword().equals(password)) {
				SessionHandler.setSession(new Session(true, "STUDENT", student.getStudentId()));
				return "redirect:/students";
			}
		}
		
		for (TeacherEntity teacher : teaRepo.findAll()) {
			if (teacher.getEmail().toLowerCase().equals(email.toLowerCase()) && teacher.getPassword().equals(password)) {
				SessionHandler.setSession(new Session(true, "TEACHER", teacher.getTeacherId()));
				return "redirect:/teachers";
			}
		}
		
		for (AdminEntity admin : adRepo.findAll()) {
			if (admin.getEmail().toLowerCase().equals(email.toLowerCase()) && admin.getPassword().equals(password)) {
				SessionHandler.setSession(new Session(true, "ADMIN", admin.getAdminId()));
				return "redirect:/admin";
			}
		}
		
		return "redirect:/pleasetryagain";
	}
	
}
