package com.libraryapp.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.libraryapp.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
		
}
