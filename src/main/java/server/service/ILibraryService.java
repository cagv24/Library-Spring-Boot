package server.service;

import java.util.List;

import server.dto.BookDTO;
import server.dto.UserDTO;

public interface ILibraryService {

	public List<BookDTO> getBooksByName(String name);

	public List<BookDTO> getBooksByGenre(String genre);

	public List<BookDTO> getWrittenBooks(String username);

	public List<BookDTO> getBooksToCheck(String username);
	
	public List<BookDTO> getPendingBooks();

	public UserDTO signIn(UserDTO user);

	public void uploadBook(BookDTO bookDTO, String username);

	public void editBook(long bookId, int bookState);

	public void rateBook(long bookId, int score, String username);
	
	public UserDTO getUserByUsername(String username);

}
