package com.libraryapp.entities;

import java.time.LocalDate; 
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOKS")
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bookId;

	private String title;
	private String author;
	private int releaseYear;
	private int edition;
	private LocalDate returnDate = null;
	private LocalDate startReservationDate = null;
	private LocalDate endReservationDate = null;
	private int timesExtended = 0;
	private boolean readyForPickUp = false;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	private User reservedByUser;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	private User theUser;
	
	public Book() {
		
	}

	public Book(String title, String author, int releaseYear, int edition) {
		super();
		this.title = title;
		this.author = author;
		this.releaseYear = releaseYear;
		this.edition = edition;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public User getTheUser() {
		return theUser;
	}

	public void setTheUser(User theUser) {
		this.theUser = theUser;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	public void setTimesExtended(int timesExtended) {
		this.timesExtended = timesExtended;
	}
	
	public int getTimesExtended() {
		return timesExtended;
	}
	
	public LocalDate getEndReservationDate() {
		return endReservationDate;
	}
	
	public void setEndReservationDate(LocalDate endReservationDate) {
		this.endReservationDate = endReservationDate;
	}
	
	public LocalDate getStartReservationDate() {
		return startReservationDate;
	}
	
	public void setStartReservationDate(LocalDate startReservationDate) {
		this.startReservationDate = startReservationDate;
	}
	
	public User getReservedByUser() {
		return reservedByUser;
	}
	
	public void setReservedByUser(User reservedByUser) {
		this.reservedByUser = reservedByUser;
	}
	
	public void setReadyForPickup(boolean readyForPickUp) {
		this.readyForPickUp = readyForPickUp;
	}
	
	public boolean getReadyForPickUp() {
		return readyForPickUp;
	}
	
}
