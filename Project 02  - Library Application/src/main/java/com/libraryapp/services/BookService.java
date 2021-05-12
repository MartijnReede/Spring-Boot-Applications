package com.libraryapp.services;

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libraryapp.DAO.BookRepository;
import com.libraryapp.DAO.UserRepository;
import com.libraryapp.entities.Book;
import com.libraryapp.entities.User;

@Service
public class BookService {

	@Autowired
	BookRepository bookRepo;
	
	@Autowired
	UserRepository usRepo;
	
	public void save(Book book) {
		bookRepo.save(book);
	}
	
	public void saveById(Long bookId) {
		bookRepo.save(bookRepo.findById(bookId).get());
	}
	
	public List<Book> findAll(){
		return (List<Book>) bookRepo.findAll();
	}
	
	public Book findById(long bookId) {
		Book book = bookRepo.findById(bookId).get();
		return book;
	}
	
	public List<Book> searchBooks(String title, String author){
		
		List<Book> searchedBooks = new ArrayList<Book>();
		
		if (title != null && author == null) {
			searchedBooks = getByTitle(title);
		} else if (title == null && author != null) {
			searchedBooks = getByAuthor(author);
		} else if (title != null && author != null) {
			searchedBooks = getByTitleAndAuthor(title, author);
		} 
		
		return searchedBooks;
	}
	
	public List<Book> getByTitle(String title){
		List<Book> books = new ArrayList<>();
		for (Book book : bookRepo.findAll()) {
			if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
				books.add(book);
			}
		}
		return books;
	}
	
	public List<Book> getByAuthor(String author){
		List<Book> books = new ArrayList<>();
		for (Book book : bookRepo.findAll()) {
			if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				books.add(book);
			}
		}	
		return books;
	}
	
	public List<Book> getByTitleAndAuthor(String title, String author){
		List<Book> books = new ArrayList<>();
		for (Book book : bookRepo.findAll()) {
			if (book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
				book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
				books.add(book);
			}
		}
		return books;
	}
	
	public void deleteById(long bookId) {
		bookRepo.deleteById(bookId);
	}
	
	public List<Book> getUnprocessedBookReservations(){
		List<Book> unprocessedBookReservations = new ArrayList<Book>();
		for (Book book : bookRepo.findAll()) {
			if (book.getReservedByUser() != null && book.getReadyForPickUp() == false) {
				unprocessedBookReservations.add(book);
			}
		}
		return unprocessedBookReservations;
	}
	
	public List<Book> getProcessedBookReservations(){
		List<Book> processedBookReservations = new ArrayList<Book>();
		for (Book book : bookRepo.findAll()) {
			if (book.getReservedByUser() != null && book.getReadyForPickUp() == true) { 
				processedBookReservations.add(book);
			}
		}
		return processedBookReservations;
	}
	
	public List<Book> convertIdsCollectionToBooksList(Collection<Long> bookIds){
		List<Book> books = new ArrayList<Book>();
		for (Long bookId : bookIds) books.add(bookRepo.findById(bookId).get());
		return books;
	}
	
	public void removeCurrentUserOfMultipleBooks(List<Book> books) {
		for (Book book : books) removeCurrentUserOfBook(book);
	}
	
	public void removeCurrentUserOfBook(Book book) {
		User currentUser = book.getTheUser();
		for (int i = 0; i < currentUser.getBooks().size(); i++) {
			if (currentUser.getBooks().get(i).getBookId() == book.getBookId()) {
				currentUser.getBooks().remove(i);
				break;
			}
		}
		usRepo.save(currentUser);
		book.setTheUser(null);
		book.setReturnDate(null);
		book.setTimesExtended(0);
		bookRepo.save(book);
	}
	
	public void removeReservation(Book book) {
		User reservedByUser = book.getReservedByUser();
		for (int i = 0; i < reservedByUser.getReservedBooks().size(); i++) {
			if (reservedByUser.getReservedBooks().get(i).getBookId() == book.getBookId()) {
				reservedByUser.getReservedBooks().remove(i);
				break;
			}
		}
		usRepo.save(reservedByUser);
		book.setStartReservationDate(null);
		book.setEndReservationDate(null);
		book.setReadyForPickup(false);
		bookRepo.save(book);
	}
	
	public void saveBookOrder(Collection<Long> selectedBookIds, User user) {
		for (Long bookId : selectedBookIds) {
			Book book = bookRepo.findById(bookId).get();
			book.setReturnDate(LocalDate.now().plusDays(20));
			book.setStartReservationDate(null);
			book.setEndReservationDate(null);
			book.setReservedByUser(null);
			book.setReadyForPickup(false);
			book.setTheUser(user);
			bookRepo.save(book);
			usRepo.save(user);
		}
	}
}
