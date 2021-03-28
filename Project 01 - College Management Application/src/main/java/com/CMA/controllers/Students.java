
package com.CMA.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CMA.DAO.CourseRepository;
import com.CMA.DAO.GradeRepository;
import com.CMA.DAO.StudentRepository;
import com.CMA.entities.CourseEntity;
import com.CMA.entities.GradeEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.other.CourseSelector;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/students")
public class Students {

	private String portalType = "STUDENT";	

	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@Autowired
	CourseSelector courseSelector;

	@GetMapping
	public String mainPage(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		StudentEntity currentStudent = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("user", currentStudent);
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(currentStudent));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(currentStudent));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(currentStudent));
		return "student-portal/student-page-main.html";
	}	
		
	@GetMapping(value="/ManageAccount")
	public String manageAccount(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("student", studRepo.findById(SessionHandler.getSession().getUserId()).get());
		return"student-portal/student-manage-account.html";
	}
	
	@PostMapping(value="changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		StudentEntity student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		if( !(oldPassword.equals(student.getPassword())) || !(newPassword.equals(newPassword2))){
			return "student-portal/student-change-password-failed.html";
		} else {
			student.setPassword(newPassword);
			studRepo.save(student);
			return "student-portal/student-change-password-succeeded.html";
		}
	}
		
	@GetMapping(value="/manageCourses")
	public String manageCourses(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		StudentEntity currentStudent = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		List<CourseEntity>availableCourses = courseSelector.getAvailableCourses(currentStudent);
		List<CourseEntity>enrolledCourses = courseSelector.getCoursesNotStarted(studRepo.findById(SessionHandler.getSession().getUserId()).get());
		model.addAttribute("availableCourses", availableCourses);
		model.addAttribute("enrolledCourses", enrolledCourses);
		return "student-portal/student-manage-courses.html";
	}
	
	@PostMapping(value="enrollForCourse")
	public String enrollForCourse(@RequestParam Long enrollingCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		
		CourseEntity course = couRepo.findById(enrollingCourse).get();
		StudentEntity student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		GradeEntity grade = new GradeEntity(0.0);
		grade.setTheCourse(course);
		grade.setTheStudent(student);
		
		student.getCourses().add(course);
		studRepo.save(student);
		couRepo.save(course);
		gradRepo.save(grade);
		
		return "redirect:/students/manageCourses";
	}
	
	@GetMapping(value="/displayGrades")
	public String displayGrades(Model model) {
	if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		StudentEntity student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		List<GradeEntity> grades = student.getGrades();
		model.addAttribute("grades", grades);
		return "student-portal/student-display-grades.html";
	}
}