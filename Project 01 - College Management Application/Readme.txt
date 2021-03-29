-- COLLEGE MANAGEMENT APPLICATION --

This is my first Spring Boot web application. The main goal was to practice with:
  - JPA
  - H2
  - Thymeleaf
  - MVC
  
Check this youtube video to see the application in action: 
   
-- FUNCTIONALITY --

This application provides an online platform where teachers and students can manage their courses and grades. 
Each user has a personal account showing only their personal information.
There are three types of accounts:
  - Student accounts
  - Teacher accounts
  - Admin accounts
  
Each account type has its own specific functionality. 

-- STUDENT ACCOUNT --

Student dashboard: 
  - The students can see all the courses they are enrolled in. 
  - The courses are divided in three categories: not started, in progress or completed. 
  - There is a button to view personal account information. Here the students can view their
    account info and change their password. 
  
Manage courses:
  - The students can enroll for available courses. Available courses are courses that are in the "not started" 
    category and have not reached the maximum amount of students yet. 
  - At the bottom of the page, the students can see for what courses they are enrolled in. 

Grades:
  - The students can view their personal grades. 
  
-- TEACHER ACCOUNT --

Teacher dashboard:
  - The teachers can see all their personal courses. These courses are also divided into three categories:
    not started, in progress or completed. The teachers can see how many students are enrolled in their courses.
  - There is a button to view personal account information. Here the teachers can view their account info and
    change their password. 
  
Create new course:
  - Teachers can create new courses here. 

Manage courses:
  - Teachers can select a specific course here. Course information will be displayed, the current stage of the
    course can be changed and grades can be assigned to all the students that are enrolled in the selected course.
  
-- ADMIN ACCOUNT --

Admin dashboard: 
  - There is a button to view personal account information. Here the admins can view their account info and
    change their password. 
  - A table is show with some data:  The amount of students and teachers that are using the application and 
    the average grade of all students. 

Manage students:
  - New student accounts can be created here. 
  - There is a button to display a table of all students. 
  - Single students can be managed by searching with their first and last name. 
  - When the student is found, their account password can be reset and all grades can be viewed of this particular
    student. 

Manage teachers: 
  - New teachers accounts can be created here. 
  - There is a button to display a table of all teachers. 
  - Single teachers can be managed by searching with their first and last name. 
  - When the teacher is found, their account password can be reset and all the courses belonging to that teacher
    are displayed in a table, along with the average grade of the students that enrolled in it. This shows 
    how well a teacher is doing. 
 
Manage admins: 
  - New admin accounts can be created here. 
  - A table is shown with all admin accounts.
  


  
 
  
