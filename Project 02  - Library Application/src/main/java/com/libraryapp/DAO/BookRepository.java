package com.libraryapp.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.libraryapp.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
