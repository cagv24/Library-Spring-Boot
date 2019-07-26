package server.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "revisers")
public class Reviser extends User {

	@Temporal(TemporalType.DATE)
	private Date lastBookDate;
	
	private int numberOfBooksToCheck;
	
	// List the reviser's books to check
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Book> booksToCheck;

	public Reviser() {
		super();
	}
	
	public Reviser(String username, String password, int userType, String email, String name, String lastName) {
		super(username, password, userType, email, name, lastName);
	}
	
	public Date getLastBookDate() {
		return lastBookDate;
	}
	public void setLastBookDate(Date lastBookDate) {
		this.lastBookDate = lastBookDate;
	}
	public int getNumberOfBooksToCheck() {
		return numberOfBooksToCheck;
	}
	public void setNumberOfBooksToCheck(int numberOfBooksToCheck) {
		this.numberOfBooksToCheck = numberOfBooksToCheck;
	}
	
	public List<Book> getBooksToCheck() {
		return booksToCheck;
	}
	public void setBooksToCheck(List<Book> booksToCheck) {
		this.booksToCheck = booksToCheck;
	}
	public void addBook(Book book) {
		this.booksToCheck.add(book);
	}
	
	public void removeBook(long bookId) {
		for (int i = 0; i < this.booksToCheck.size(); i++) {	
			if (this.booksToCheck.get(i).getId() == bookId) {
				this.booksToCheck.remove(i);
				break;
			}
		}
	}
}