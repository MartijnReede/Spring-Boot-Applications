package com.CMA;


import java.sql.Date;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.CMA.DAO.AdminRepository;
import com.CMA.DAO.CourseRepository;
import com.CMA.DAO.GradeRepository;
import com.CMA.DAO.StudentRepository;
import com.CMA.DAO.TeacherRepository;
import com.CMA.entities.AdminEntity;
import com.CMA.entities.CourseEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.entities.TeacherEntity;

@SpringBootApplication
public class CollegeManagementApplication {

	@Autowired
	AdminRepository adRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository graRepo;
	
	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	TeacherRepository teaRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(CollegeManagementApplication.class, args);
	}
	

	@Bean
	CommandLineRunner runner() {
		
		return args -> {
			
			//USERS
			AdminEntity ad1 = new AdminEntity("Lucia", "Willems", "admin@gmail.com", "admin");
			AdminEntity ad2 = new AdminEntity("Martijn", "Reede", "martijn.reede@gmail.com", "luciaislief");
			
			TeacherEntity teach1 = new TeacherEntity("Cyrille", "Jones", "teacher@gmail.com", "teacher");
			TeacherEntity teach2 = new TeacherEntity("Michele", "Coppee", "97621@cma-teacher.com", "imnotateachers3");
			TeacherEntity teach3 = new TeacherEntity("Magriet", "Breevoort", "92893@cma-teacher.com", "idontlikestudents!");
			
			StudentEntity stud1 = new StudentEntity("Kevin", "Leijnse", "kevin@gmail.com", "student");
			StudentEntity stud2 = new StudentEntity("Jacky", "June-Kroes", "jacky@gmail.com", "jacky");
			StudentEntity stud3 = new StudentEntity("Tijn", "De Vries", "41312@cma-student.com", "tryme3!");
			StudentEntity stud4 = new StudentEntity("Carlos", "Molero", "45562@cma-student.com", "iloveyou2");
			
			//Current date in app = 25-03-2021
			
			CourseEntity course1 = new CourseEntity("Programming", "NOTSTARTED", Date.valueOf("2021-04-10"), Date.valueOf("2021-06-10"), 8);
			CourseEntity course2 = new CourseEntity("Computer Science", "INPROGRESS", Date.valueOf("2021-01-01"), Date.valueOf("2021-05-01"), 8);
			CourseEntity course3 = new CourseEntity("Algorithms", "COMPLETED", Date.valueOf("2020-12-15"), Date.valueOf("2021-02-15"), 5);
			CourseEntity course4 = new CourseEntity("Data Structures", "NOTSTARTED", Date.valueOf("2021-03-28"), Date.valueOf("2021-05-28"), 5);
			
			//SAVE ALL ENTITIES
			
			adRepo.save(ad1);
			adRepo.save(ad2);
			
			teaRepo.save(teach1);
			teaRepo.save(teach2);
			teaRepo.save(teach3);
			
			studRepo.save(stud1);
			studRepo.save(stud2);
			studRepo.save(stud3);
			studRepo.save(stud4);
			
			couRepo.save(course1);
			couRepo.save(course2);
			couRepo.save(course3);
			couRepo.save(course4);
			
			//SET RELATIONS AND SAVE AGAIN
			
			course1.setTheTeacher(teach1);
			course2.setTheTeacher(teach1);
			course3.setTheTeacher(teach1);
			course4.setTheTeacher(teach1);
			
			teach1.setCourses(Arrays.asList(course1, course2, course3, course4));
			
			couRepo.save(course1);
			couRepo.save(course2);
			couRepo.save(course3);
			couRepo.save(course4);
			
			teaRepo.save(teach1);
			
		};
		
	}
}
