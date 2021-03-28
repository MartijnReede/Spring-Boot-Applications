package com.CMA.controllers;
import java.util.ArrayList;
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
import com.CMA.DAO.TeacherRepository;
import com.CMA.entities.CourseEntity;
import com.CMA.entities.GradeEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.entities.TeacherEntity;
import com.CMA.other.CourseSelector;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/teachers")
public class Teachers {

	private String portalType = "TEACHER";	
	
	@Autowired
	TeacherRepository teachRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@Autowired
	CourseSelector courseSelector;
	
	@GetMapping
	public String mainPage(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity currentTeacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("user", currentTeacher);
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(currentTeacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(currentTeacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(currentTeacher));
		return "teacher-portal/teacher-page-main.html";
	}

	@GetMapping(value="/ManageAccount")
	public String manageTeacherAccount(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("teacher", teacher);
		return "teacher-portal/teacher-manage-account.html";
	}
	
	@PostMapping(value="/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		if ( !(oldPassword.equals(teacher.getPassword())) || !(newPassword.equals(newPassword2)) ) {
			return "teacher-portal/teacher-change-password-failed.html";
		} else {
			teacher.setPassword(newPassword);
			teachRepo.save(teacher);
			return "teacher-portal/teacher-change-password-succeeded.html";
		}
	}
	
	@GetMapping(value="createNewCourse")
	public String createNewClass(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("course", new CourseEntity());
		return"teacher-portal/teacher-create-new-course.html";
	}
	
	@PostMapping(value="/saveCourse")
	public String saveCourse(CourseEntity newCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		newCourse.setTheTeacher(teacher);
		couRepo.save(newCourse);
		return"teacher-portal/teacher-course-saved.html";
	}
	
	@GetMapping(value="/manageCourses")
	public String manageCourses(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(teacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(teacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(teacher));
		return"teacher-portal/teacher-manage-courses.html";
	}
	
	@GetMapping(value="/manageTheCourse")
	public String manageTheCourse(@RequestParam Long chosenCourse, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		CourseEntity course = couRepo.findById(chosenCourse).get();
		model.addAttribute("chosenCourse", course);
		
		List<StudentEntity> enrolledStudents = course.getStudents();
		
		List<GradeEntity> grades = new ArrayList<>();
		
		for (StudentEntity student : enrolledStudents) {
			for (GradeEntity grade : student.getGrades()) {
				if (grade.getTheStudent() == student && grade.getTheCourse().getCourseId() == chosenCourse) {
					grades.add(grade);
				}
			}
		}
	
		model.addAttribute("grades", grades);
		return "teacher-portal/teacher-manage-chosen-course.html";
	}
	
	@PostMapping(value="/setGrade")
	public String setGrade(@RequestParam Long gradeId, @RequestParam Double courseGrade, @RequestParam Long chosenCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		GradeEntity grade = gradRepo.findById(gradeId).get();
		grade.setGrade(courseGrade);
		gradRepo.save(grade);
		return "redirect:/teachers/manageTheCourse?chosenCourse=" + chosenCourse;
	}
	

	
	@PostMapping(value="/saveCourseStage")
	public String saveCourseStage(@RequestParam String updateStage, @RequestParam Long chosenCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		CourseEntity course = couRepo.findById(chosenCourse).get();
		course.setStage(updateStage);
		couRepo.save(course);
		return "redirect:/teachers/manageTheCourse?chosenCourse=" + chosenCourse;
	}
}
