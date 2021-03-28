package com.CMA.other;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.CMA.DAO.CourseRepository;
import com.CMA.entities.CourseEntity;
import com.CMA.entities.StudentEntity;
import com.CMA.entities.TeacherEntity;

public class CourseSelector {

	@Autowired
	CourseRepository couRepo;
	
	public CourseSelector() {
		
	}
	
	public List<CourseEntity> getCoursesNotStarted(TeacherEntity teacher){
		List<CourseEntity> coursesNotStarted = new ArrayList<>();
		for (CourseEntity course : couRepo.findAll()) {
			if (course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("NOTSTARTED")) coursesNotStarted.add(course); 
		}
		return coursesNotStarted;
	}
	
	public List<CourseEntity> getCoursesInProgress(TeacherEntity teacher){
		List<CourseEntity> coursesInProgress = new ArrayList<>();
		for (CourseEntity course : couRepo.findAll()) {
			if(course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("INPROGRESS")) coursesInProgress.add(course);
		}
		return coursesInProgress;
	}
	
	public List<CourseEntity> getCoursesCompleted(TeacherEntity teacher){
		List<CourseEntity> coursesCompleted = new ArrayList<>();
		for (CourseEntity course : couRepo.findAll()) {
			if (course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("COMPLETED")) coursesCompleted.add(course);
		}
		return coursesCompleted;
	}
	
	public List<CourseEntity> getCoursesNotStarted(StudentEntity student){
		List<CourseEntity> coursesNotStarted = new ArrayList<>();
		for (CourseEntity course : student.getCourses()) {
			if (course.getStage().equals("NOTSTARTED")) coursesNotStarted.add(course); 
		}
		return coursesNotStarted;
	}
	
	public List<CourseEntity> getCoursesInProgress(StudentEntity student){
		List<CourseEntity> coursesInProgress = new ArrayList<>();
		for (CourseEntity course : student.getCourses()) {
			if (course.getStage().equals("INPROGRESS")) coursesInProgress.add(course); 
		}
		return coursesInProgress;
	}
	
	public List<CourseEntity> getCoursesCompleted(StudentEntity student){
		List<CourseEntity> coursesCompleted = new ArrayList<>();
		for (CourseEntity course : student.getCourses()) {
			if (course.getStage().equals("COMPLETED")) coursesCompleted.add(course); 
		}
		return coursesCompleted;
	}
	
	public List<CourseEntity> getAvailableCourses(StudentEntity student){
		List<CourseEntity> availableCourses = new ArrayList<>();
		for (CourseEntity course : couRepo.findAll()) {
			if(course.getStage().equals("NOTSTARTED") && course.getMaxStudents() > course.getStudents().size() &&
				!(course.getStudents().contains(student))) { 
				availableCourses.add(course);
			}
		}
		return availableCourses;
	}

	
	
}
