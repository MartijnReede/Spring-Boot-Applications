package com.CMA.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GradeEntity{

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long gradeId;
	
	private double grade;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="course_Id")
	private CourseEntity theCourse;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="student_Id")
	private StudentEntity theStudent;

	public GradeEntity() {
		
	}
	
	public GradeEntity(double grade) {
		super();
		
		this.grade = grade;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public StudentEntity getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(StudentEntity theStudent) {
		this.theStudent = theStudent;
	}

	public long getGradeId() {
		return gradeId;
	}

	public void setGradeId(long gradeId) {
		this.gradeId = gradeId;
	}

	public CourseEntity getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(CourseEntity theCourse) {
		this.theCourse = theCourse;
	}
	
	
}
