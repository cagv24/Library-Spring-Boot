package server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import server.entities.Author;
import server.entities.Book;
import server.entities.Editor;
import server.entities.Reviser;
import server.entities.User;

@Repository
public class UserDAO implements IUserDAO {

	@PersistenceContext
	EntityManager em;
	
	//Returns the User whose username is the parameter 'username'.
	@Override
	public User getUser(String username) {
		return em.find(User.class, username);
	}

	//This method creates a new Author.
	@Override
	@Transactional
	public void createAuthor(Author author) {
		em.persist(author);
	}
	
	//This method creates a new Reviser.
	@Override
	@Transactional
	public void createReviser(Reviser reviser) {
		em.persist(reviser);
	}
	
	//This method updates an User.
	@Override
	@Transactional
	public void updateUser(User user) {
		em.merge(user);
	}
	
	//This method returns System's editor.
	@Override
	public Editor getEditor() {
		return em.find(Editor.class, "editor");
	}

	//This method returns Editor's pending books.
	@Override
	@Transactional
	public List<Book> getPendingBooks() {
		Editor editor = this.getEditor();
		List<Book> books = new ArrayList<Book>(editor.getPendingBooks());
		return books;
	}
	
	//This method adds a book to it's pending list.
	@Override
	@Transactional
	public void assignBookToEditor(Book book) {
		Editor editor = this.getEditor();
		editor.addBook(book);
	}
	
	//This method removes a book to it's pending list.
	@Override
	@Transactional
	public void unassignBookToEditor(Book book) {
		Editor editor = this.getEditor();
		editor.removeBook(book.getId());
	}

	//This method returns all System's revisers ordered by last date of assignment.
	@Override
	public List<Reviser> getAllRevisersOrderByDate() {
		List<Reviser> revisers = em.createQuery("SELECT r FROM Reviser r ORDER BY r.lastBookDate", Reviser.class)
				.getResultList();
		return revisers;
	}
	
	//This method assigns a Book to the revisers who have to check it.
	@Override
	@Transactional
	public void assignBookToRevisers(List<Reviser> revisers, Book book) {
		Reviser mergedReviser;
		for (Reviser reviser : revisers) {
			mergedReviser = em.merge(reviser);
			mergedReviser.addBook(book);
		}
	}
}
