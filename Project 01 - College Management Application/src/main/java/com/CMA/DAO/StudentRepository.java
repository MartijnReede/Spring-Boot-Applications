package com.CMA.DAO;

import org.springframework.data.repository.CrudRepository;

import com.CMA.entities.StudentEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Long> {

}
