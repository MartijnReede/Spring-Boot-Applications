package com.CMA.entities;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class StudentEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long studentId;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="theStudent")
	private List<GradeEntity> grades;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, 
			fetch = FetchType.LAZY)
	@JoinTable(name="student_course", 
				joinColumns = @JoinColumn(name="student_id"),
				inverseJoinColumns =@JoinColumn(name="course_id"))
	private List<CourseEntity> courses;
	
	public StudentEntity() {
		
	}
	
	
	
	public List<CourseEntity> getCourses() {
		return courses;
	}



	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}



	public StudentEntity(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}
	
	
}
