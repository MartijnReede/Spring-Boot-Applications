package com.CMA.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CMA.DAO.StudentRepository;
import com.CMA.entities.GradeEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/admin")
public class AdminStudentManagement {

	private String portalType="ADMIN";	
	
	@Autowired
	StudentRepository studRepo;
	
	@GetMapping(value="/ManageStudents")
	public String manageStudents(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("student", new StudentEntity());
		return "admin-portal/admin-manage-students.html";
	}
	
	@PostMapping(value="/saveStudent")
	public String saveStudent(StudentEntity student, @RequestParam String password) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		student.setPassword(password);
		studRepo.save(student);
		return "redirect:/admin/studentSaved";
	}
	
	@GetMapping(value="/studentSaved")
	public String studentSaved() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-saved.html";
	}
	
	@PostMapping(value="/searchStudent")
	public String searchStudent(@RequestParam String firstName, @RequestParam String lastName) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		for (StudentEntity student : studRepo.findAll()) {
			if (student.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && 
					student.getLastName().toLowerCase().equals(lastName.toLowerCase())) {
					long studentId = student.getStudentId();
				return "redirect:/admin/foundStudent/" + studentId;
			}
		}
		return "redirect:/admin/studentNotFound";
	}
	
	@GetMapping(value="/foundStudent/{studentId}")
	public String foundStudent(@PathVariable long studentId, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Optional<StudentEntity> student = studRepo.findById(studentId);
		if (student.isEmpty()) {
			return "redirect:/admin/studentNotFound";
		} else {
			model.addAttribute("student", student.get());
			
			List<GradeEntity>grades = new ArrayList<>();
			for (GradeEntity grade : student.get().getGrades()) {
				if (grade.getGrade() > 0.0) {
					grades.add(grade);
				}
			}
			model.addAttribute("grades", grades);
		}	
		return	"admin-portal/admin-single-student.html";
	}
	
	@GetMapping(value = "/studentNotFound")
	public String studentNotFound() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-not-found.html";
	}
	
	@PostMapping(value = "/resetStudentPassword")
	public String resetPassword(@RequestParam String password, @RequestParam long studentId) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		StudentEntity student = studRepo.findById(studentId).get();
		student.setPassword(password);
		studRepo.save(student);
		return "redirect:/admin/studentPasswordReset";
	}
	
	@GetMapping(value = "/studentPasswordReset")
	public String passwordIsReset() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-password-reset.html";
	}
	
	@GetMapping(value="/displayAllStudents")
	public String displayAllStudents(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		List<StudentEntity> allStudents = (List<StudentEntity>) studRepo.findAll();
		model.addAttribute("allStudents", allStudents);
		return "admin-portal/admin-all-students.html";
	}
}
