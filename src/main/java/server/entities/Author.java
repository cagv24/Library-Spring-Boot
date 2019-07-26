package server.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "authors")
public class Author extends User{

	// List of the written books
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	List<Book> writtenBooks;

	public Author() {
		super();
	}
	
	public Author(String username, String password, int userType, String email, String name, String lastName) {
		super(username, password, userType, email, name, lastName);
	}
	
	public List<Book> getWrittenBooks() {
		return writtenBooks;
	}	

	public void setWrittenBooks(List<Book> writtenBooks) {
		this.writtenBooks = writtenBooks;
	}
	
	public void addBook(Book book) {
		this.writtenBooks.add(book);
	}
	
	public void removeBook(long bookId) {
		for (int i = 0; i < this.writtenBooks.size(); i++) {	
			if (this.writtenBooks.get(i).getId() == bookId) {
				this.writtenBooks.remove(i);
				break;
			}
		}
	}
}
