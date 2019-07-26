package server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import server.dao.IBookDAO;
import server.dao.IUserDAO;
import server.dto.BookDTO;
import server.dto.LibraryDTOBuilder;
import server.dto.UserDTO;
import server.entities.Author;
import server.entities.Book;
import server.entities.Reviser;
import server.entities.User;
import server.exceptions.BookNotAssigned;
import server.exceptions.BookNotEdited;
import server.exceptions.BookNotFound;
import server.exceptions.BookNotUploaded;
import server.exceptions.BookRateNotAssigned;
import server.exceptions.BooksNotFound;
import server.exceptions.NoRevisersAvailable;
import server.exceptions.NotEnoughPrivileges;
import server.exceptions.UserNotCreated;
import server.exceptions.WrongParameters;
import server.utils.BookState;
import server.utils.UserType;

@Component
public class LibraryService implements ILibraryService {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IBookDAO bookDAO;
	
	private static final Logger log = LoggerFactory.getLogger(LibraryService.class);

	public LibraryService() {

	}

	//This method returns all books whose name contains the parameter 'name'. 
	@Override
	public List<BookDTO> getBooksByName(String name) {
		List<Book> books = new ArrayList<>();
		books = bookDAO.getBooksByName(name);
		
		//Throws the exception BooksNotFound if the query has no results.
		if (books == null || books.isEmpty())
			throw new BooksNotFound();
		
		return LibraryDTOBuilder.bookListToBookDTOList(books);
	}

	//This method returns all books whose genre contains the parameter 'genre'
	@Override
	public List<BookDTO> getBooksByGenre(String genre) {
		List<Book> books = new ArrayList<>();
		books = bookDAO.getBooksByGenre(genre);
		
		if (books == null || books.isEmpty())
			throw new BooksNotFound();
		
		return LibraryDTOBuilder.bookListToBookDTOList(books);
	}

	//This method returns all books written by an author specified with the parameter 'username'
	@Override
	public List<BookDTO> getWrittenBooks(String username) {
		List<BookDTO> books = new ArrayList<>();
		
		Author user = (Author) userDAO.getUser(username);
		books = LibraryDTOBuilder.bookListToBookDTOList(user.getWrittenBooks());
		
		if (books == null || books.isEmpty())
			throw new BooksNotFound();
		
		return books;
	}

	//This method returns all books pending to be checked by a reviser specified with the parameter 'username'
	@Override
	public List<BookDTO> getBooksToCheck(String username) {
		List<BookDTO> books = new ArrayList<>();

		Reviser reviser = (Reviser) userDAO.getUser(username);		
		books = LibraryDTOBuilder.bookListToBookDTOList(reviser.getBooksToCheck());
		
		if (books == null || books.isEmpty())
			throw new BooksNotFound();
		
		return books;
	}

	//This method returns all books pending to be accepted by the editor.
	@Override
	public List<BookDTO> getPendingBooks() {
		List<BookDTO> books = new ArrayList<>();
	
		books = LibraryDTOBuilder.bookListToBookDTOList(userDAO.getPendingBooks());
		
		return books;
	}
	
	//This method is called when a user wants to Sign in in the app.
	//Returns user's new token when it's successful.
	@Override
	public UserDTO signIn(UserDTO user){
		if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null
				|| user.getName() == null || user.getLastName() == null || (user.getUserType() != UserType.AUTHOR.getId() &&
				user.getUserType() != UserType.REVISER.getId()) || user.getUsername().equals("") 
				|| user.getEmail().equals("") || user.getPassword().equals("") || user.getName().equals("") 
				|| user.getLastName().equals(""))
			throw new WrongParameters();
					
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if (user.getUserType() == UserType.AUTHOR.getId())
				userDAO.createAuthor(LibraryDTOBuilder.userDTOToAuthor(user));
			else if (user.getUserType() == UserType.REVISER.getId())
				userDAO.createReviser(LibraryDTOBuilder.userDTOToReviser(user));
			return user;
		} catch(Exception e) {
			log.error("Library service: signIn " + e.getMessage());
			throw new UserNotCreated();
		}
	}
	
	//This method is executed when an author wants to upload a book into the app.
	@Override
	public void uploadBook(BookDTO bookDTO, String username) {
		
		//Checks if the user is an Author by checking the parameter 'token'.
		Author author = (Author) userDAO.getUser(username);
		Book book = LibraryDTOBuilder.bookDTOToBook(bookDTO);
		author.addBook(book);
		
		try {
			bookDAO.uploadBook(book, author);
		} catch(Exception e) {
			log.error("LibraryService: uploadBook" + e.getMessage());
			throw new BookNotUploaded();
		}
	}

	//This method is is executed when the editor wants to approve or discard the book.
	@Override
	public void editBook(long bookId, int bookState) {

		if (bookId == 0 || (bookState != BookState.REVIEW.getState() && bookState != BookState.DISCARDED.getState()))
			throw new WrongParameters();
		
		//Checks if the book exists and if it's currently in Edition status.
		Book book = bookDAO.getBookById(bookId);
		if (book == null)
			throw new BookNotFound();
		if(book.getState() != BookState.UPLOADED.getState())
			throw new NotEnoughPrivileges();

		book.setState(bookState);
		List<Reviser> freeRevisers = new ArrayList<Reviser>();
		if (bookState == BookState.REVIEW.getState()) {
			freeRevisers = new ArrayList<Reviser>(this.getFreeRevisers());
			if(freeRevisers == null || freeRevisers.isEmpty() || freeRevisers.size() < 3)
				throw new NoRevisersAvailable();
		}
		
		try {
			if (bookState == BookState.REVIEW.getState())
				bookDAO.markBookAsPublishable(book, freeRevisers);
			else {
				book.setState(BookState.DISCARDED.getState());
				bookDAO.markBookAsDiscarded(book);
			}
		} catch (Exception e) {
			log.error("LibraryService: editBook" + e.getMessage());
			throw new BookNotEdited();
		}
	}

	//This method is executed when a reviser wants to rate a book that's in his/her list of assignments.
	public void rateBook(long bookId, int score, String username) {
		
		//Input validation
		if(bookId == 0 || !(score >= 0 && score <= 10))
			throw new WrongParameters();
		
		Reviser reviser = (Reviser) userDAO.getUser(username);	
		
		//Checks if the Reviser has this book assigned.
		List<Book> books = reviser.getBooksToCheck();
		if(books.isEmpty() || books == null)
			throw new BookNotFound();
		
		Book book2Rate = null;
		for(Book book: books) {
			if (book.getId() == bookId) {
				book2Rate = book;
				break;
			}
		}
				
		if (book2Rate == null)
			throw new BookNotAssigned();
		
		//Assigns book score.
		if(book2Rate.getScore() != null) {
			//Book has already 3 scores.
			if(book2Rate.getScore().size() == 3)
				throw new BookNotAssigned();
			
			//Book has at least 1 score.	
			else
				book2Rate.addScore(score);
		}
		//Book has no rates yet.
		else {
			List<Integer> scores = new ArrayList<Integer>();
			scores.add(score);
			book2Rate.setScore(scores);
		}
		
		reviser.removeBook(book2Rate.getId());
		
		//Checks if the individual score is less than 2 so it's discarded
		if (score <= 2)
			book2Rate.setState(BookState.DISCARDED.getState());
		
		//Checks if the book rating is done
		if (book2Rate.getScore().size() == 3 && book2Rate.getState() != BookState.DISCARDED.getState()) {
			int totalScore = 0;
			for(Integer individualScore : book2Rate.getScore())
				totalScore += individualScore;
			
			//Checks the total score to publish or discard the book.
			if(totalScore < 10)
				book2Rate.setState(BookState.DISCARDED.getState());
			else
				book2Rate.setState(BookState.PUBLISHED.getState());
		}
		
		try {
		 reviser.setNumberOfBooksToCheck(reviser.getNumberOfBooksToCheck() - 1);
		 bookDAO.rateBook(book2Rate, reviser);
		} catch (Exception e) {
			throw new BookRateNotAssigned();
		}
	}
	
	//This method returns the 3 first free revisers to assign a book for them to check it.
	private List<Reviser> getFreeRevisers() {
		List<Reviser> revisers = new ArrayList<>();
		revisers = userDAO.getAllRevisersOrderByDate();
		Reviser aux;

		// Order by numberOfBooksToCheck
		for (int i = 0; i < revisers.size() - 1; i++)
			for (int j = 0; j < revisers.size() - i - 1; j++)
				if (revisers.get(j + 1).getNumberOfBooksToCheck() < revisers.get(j).getNumberOfBooksToCheck()) {
					aux = revisers.get(j + 1);
					revisers.set(j + 1, revisers.get(j));
					revisers.set(j, aux);
				}

		// Adds the first three revisers (from the ordered list) to a new list of free revisers.
		List<Reviser> freeRevisers = new ArrayList<>();
		Date currentDate = new Date();
		for (Reviser reviser : revisers) {
			reviser.setLastBookDate(currentDate);
			reviser.setNumberOfBooksToCheck(reviser.getNumberOfBooksToCheck() + 1);
			freeRevisers.add(reviser);
			if (freeRevisers.size() == 3) {
				break;
			}
		}

		return freeRevisers;
	}
	
	//This method returns the User's DTO.
	@Override
	public UserDTO getUserByUsername(String username) {
		User user = userDAO.getUser(username);	
		return LibraryDTOBuilder.userToUserDTO(user);
	}
}
