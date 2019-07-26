package server.dao;

import java.util.List;

import server.entities.Author;
import server.entities.Book;
import server.entities.Editor;
import server.entities.Reviser;
import server.entities.User;

public interface IUserDAO {
	
	public User getUser(String username);	
	
	public void createAuthor(Author author);
	
	public void createReviser(Reviser reviser);	
	
	public void updateUser(User user);	
	
	public Editor getEditor();
	
	public List<Book> getPendingBooks();
	
	public void assignBookToEditor(Book book);
	
	public void unassignBookToEditor(Book book);
	
	public List<Reviser> getAllRevisersOrderByDate();
	
	public void assignBookToRevisers(List<Reviser> revisers, Book book);
	
}
