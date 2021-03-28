package com.CMA.controllers;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CMA.DAO.GradeRepository;
import com.CMA.DAO.TeacherRepository;
import com.CMA.entities.CourseEntity;
import com.CMA.entities.GradeEntity;
import com.CMA.entities.TeacherEntity;
import com.CMA.session.SessionHandler;

@Controller
@RequestMapping(value="/admin")
public class AdminTeacherManagement {

	private String portalType="ADMIN";	
		
	@Autowired
	TeacherRepository teachRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@GetMapping(value="/ManageTeachers")
	public String manageTeachers(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("teacher", new TeacherEntity());
		return "admin-portal/admin-manage-teachers.html";
	}
	
	@PostMapping(value="saveTeacher")
	public String saveTeacher(TeacherEntity teacher, @RequestParam String password) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		teacher.setPassword(password);
		teachRepo.save(teacher);
		return "redirect:/admin/teacherSaved";
	}
		
	@GetMapping(value="/teacherSaved")
	public String teacherSaved() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-saved.html";
	}
	
	@PostMapping(value="/searchTeacher")
	public String searchTeacher(@RequestParam String firstName, @RequestParam String lastName) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		for (TeacherEntity teacher : teachRepo.findAll()) {
			if (teacher.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && 
					teacher.getLastName().toLowerCase().equals(lastName.toLowerCase())) {
					long teacherId = teacher.getTeacherId();
				return "redirect:/admin/foundTeacher/" + teacherId;
			}
		}
		return "redirect:/admin/TeacherNotFound";
	}
	
	@GetMapping(value="/TeacherNotFound")
	public String teacherNotFound() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-not-found.html";
	}
	
	@GetMapping(value="/foundTeacher/{teacherId}")
	public String foundTeacher(@PathVariable long teacherId, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Optional<TeacherEntity> teacher = teachRepo.findById(teacherId);
		if (teacher.isEmpty()) {
			return "redirect:/admin/TeacherNotFound";
		} else {
			model.addAttribute("teacher", teacher.get());
			List<CourseEntity> courses = teacher.get().getCourses();
			Map<String, Double> averageGrades = new HashMap<String, Double>();
			
			for(CourseEntity course : courses) {
				double totalOfGrades = 0.0;
				double count = 0.0;
				double averageGrade = 0.0;
				for (GradeEntity grade : gradRepo.findAll() ) {
					if(course.getCourseId() == grade.getTheCourse().getCourseId() && grade.getGrade() > 0.0) {
						totalOfGrades += grade.getGrade();
						count+=1;
					}
					
				}	
				
				averageGrade = totalOfGrades / count;
				
				if(averageGrade > 0.0){
					averageGrades.put(course.getCourseName(), averageGrade);
				
				System.out.println("TESTING:" + course.getCourseName() + "GRADE" + averageGrade);
				}
				
				
				
				model.addAttribute("averageGrades", averageGrades);
			}	
		}	
		return	"admin-portal/admin-single-teacher.html";
	}
	
	@PostMapping(value = "/resetTeacherPassword")
	public String resetPassword(@RequestParam String password, @RequestParam long teacherId) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		TeacherEntity teacher = teachRepo.findById(teacherId).get();
		teacher.setPassword(password);
		teachRepo.save(teacher);
		return "redirect:/admin/teacherPasswordReset";
	}
	
	@GetMapping(value = "/teacherPasswordReset")
	public String passwordIsReset() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-password-reset.html";
	}
	
	@GetMapping(value="/displayAllTeachers")
	public String displayAllTeachers(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		List<TeacherEntity> allTeachers = (List<TeacherEntity>) teachRepo.findAll();
		model.addAttribute("allTeachers", allTeachers);
		return "admin-portal/admin-all-teachers.html";
	}
}
