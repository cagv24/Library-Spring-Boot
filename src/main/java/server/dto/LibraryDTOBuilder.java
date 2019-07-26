package server.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.entities.Author;
import server.entities.Book;
import server.entities.Reviser;
import server.entities.User;

public class LibraryDTOBuilder {

	public static BookDTO bookToBookDTO(Book book) {
		return new BookDTO(book.getId(), book.getName(), book.getGenre(), book.getFormat(), book.getAutor(),
				book.getPublicationDate(), book.getState());
	}
	
	public static Book bookDTOToBook(BookDTO bookDTO) {
		return new Book(bookDTO.getName(), bookDTO.getGenre(), bookDTO.getFormat(), bookDTO.getAutor(), new Date());
	}

	public static List<BookDTO> bookListToBookDTOList(List<Book> books) {
		List<BookDTO> booksDTO = new ArrayList<>();
		for (Book book : books) {
			booksDTO.add(bookToBookDTO(book));
		}
		return booksDTO;
	}

	public static User userDTOToUser(UserDTO user) {
		return new User(user.getUsername(), user.getPassword(), user.getUserType(), user.getEmail(), user.getName(),
				user.getLastName());
	}

	public static Author userDTOToAuthor(UserDTO user) {
		return new Author(user.getUsername(), user.getPassword(), user.getUserType(), user.getEmail(), user.getName(),
				user.getLastName());
	}

	public static Reviser userDTOToReviser(UserDTO user) {
		return new Reviser(user.getUsername(), user.getPassword(), user.getUserType(), user.getEmail(), user.getName(),
				user.getLastName());
	}
	
	public static UserDTO userToUserDTO(User user) {
		return new UserDTO(user.getUsername(), "", user.getUserType(), user.getEmail(), user.getName(), user.getLastName());
	}
}
