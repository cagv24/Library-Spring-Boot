package server.dao;

import java.util.List;

import server.entities.Author;
import server.entities.Book;
import server.entities.Reviser;

public interface IBookDAO {

	public List<Book> getBooksByName(String name);
	
	public List<Book> getBooksByGenre(String genre);
	
	public List<Book> getBooksByState(int state);
	
	public Book getBookById(long id);
	
	public void createBook(Book book);
	
	public void updateBook(Book book);
	
	public void uploadBook(Book book, Author author);
	
	public void markBookAsPublishable(Book book, List<Reviser> revisers);
	
	public void markBookAsDiscarded(Book book);
	
	public void rateBook(Book book, Reviser reviser);
	
}
