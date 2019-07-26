package server.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "editors")
public class Editor extends User {

	//List pending books to be accepted
	@OneToMany(fetch = FetchType.LAZY)
	private List<Book> pendingBooks;	
	
	public Editor() {
		super();
	}
	
	public Editor(String username, String password, int userType, String email, String name, String lastName) {
		super(username, password, userType, email, name, lastName);
	}

	public List<Book> getPendingBooks() {
		return this.pendingBooks;
	}

	public void setPendingBooks(List<Book> pendingBooks) {
		this.pendingBooks = pendingBooks;
	}	
	
	public void addBook(Book book) {
		this.pendingBooks.add(book);
	}
	
	public void removeBook(long bookId) {
		for (int i = 0; i < this.pendingBooks.size(); i++) {	
			if (this.pendingBooks.get(i).getId() == bookId) {
				this.pendingBooks.remove(i);
				break;
			}
		}
	}
}