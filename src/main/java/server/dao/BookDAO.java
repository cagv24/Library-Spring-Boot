package server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import server.entities.Author;
import server.entities.Book;
import server.entities.Reviser;

@Repository
public class BookDAO implements IBookDAO{	
	
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private IUserDAO userDAO;
	
	//This method returns all books whose name contains the string 'name'.
	@Override
	public List<Book> getBooksByName(String name) {
		return em.createQuery("select b from Book b where LOWER(b.name) like :name AND b.state = 3", Book.class).setParameter("name", "%"+name.toLowerCase()+"%").getResultList();
	}
	
	//This method returns all books whose genre contains the string 'genre'.
	@Override
	public List<Book> getBooksByGenre(String genre) {
		return em.createQuery("SELECT b FROM Book b WHERE LOWER(b.genre) like :genre AND b.state = 3", Book.class).setParameter("genre", "%"+genre.toLowerCase()+"%").getResultList();
	}
	
	//This method returns all books whose state is the parameter 'state'.
	@Override
	public List<Book> getBooksByState(int state) {
		return em.createQuery("SELECT b FROM Book b WHERE b.state = :state ", Book.class).setParameter("state", state).getResultList();
	}
	
	//This method returns the book whose id is 'id'.
	@Override
	public Book getBookById(long id) {
		return em.find(Book.class, id);
	}

	//This method is called when a book is added into the system.
	@Override
	@Transactional
	public void createBook(Book book){
		em.persist(book);
	}
	
	//This method updates a book.
	@Override
	@Transactional
	public void updateBook(Book book) {
		em.merge(book);
	}	
	
	//This method uploads a book in the system and add it to it's Author's written list.
	@Override
	@Transactional
	public void uploadBook(Book book, Author author) {
		this.createBook(book);
		userDAO.updateUser(author);
		userDAO.assignBookToEditor(book);
	}
	
	//This method is called when an Editor marks a book as publishable.
	@Override
	@Transactional
	public void markBookAsPublishable(Book book, List<Reviser> revisers) {
		this.updateBook(book);
		userDAO.unassignBookToEditor(book);
		userDAO.assignBookToRevisers(revisers, book);
	}
	
	//This method is called when an Editor marks a book as discarded.
	@Override
	@Transactional
	public void markBookAsDiscarded(Book book) {
		userDAO.unassignBookToEditor(book);
		this.updateBook(book);
	}
	
	//This method is called when a Reviser rates a book of it's list.
	@Override
	@Transactional
	public void rateBook(Book book, Reviser reviser) {
		this.updateBook(book);
		userDAO.updateUser(reviser);
	}
}