package com.CMA.entities;

import java.sql.Date; 
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CourseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long courseId;
	
	private String courseName;
	private String stage;
	private Date startDate;
	private Date endDate;
	private int maxStudents;
	
	@OneToMany(mappedBy="theCourse")
	List<GradeEntity> grades;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinColumn(name="teacher_Id")
	private TeacherEntity theTeacher;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, 
			fetch = FetchType.LAZY)
	@JoinTable(name="student_course", 
				joinColumns = @JoinColumn(name="course_id"),
				inverseJoinColumns =@JoinColumn(name="student_id"))
	private List<StudentEntity> students;
	
	public CourseEntity() {
		
	}

	public List<StudentEntity> getStudents() {
		return students;
	}



	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}



	public CourseEntity(String courseName, String stage, Date startDate, Date endDate, int maxStudents) {
		super();
		this.courseName = courseName;
		this.stage = stage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.maxStudents = maxStudents;
	}


	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}

	public TeacherEntity getTheTeacher() {
		return theTeacher;
	}

	public void setTheTeacher(TeacherEntity theTeacher) {
		this.theTeacher = theTeacher;
	}
	
	
}
