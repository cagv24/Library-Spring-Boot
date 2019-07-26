package server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import server.dto.BookDTO;
import server.service.LibraryService;
import server.exceptions.*;

@RestController
@RequestMapping("/libreria/editor")
@CrossOrigin
public class EditorController {

	@Autowired
	LibraryService service;

	@RequestMapping(value = "/libros", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<BookDTO>> getPendingBooks() {
		List<BookDTO> books = new ArrayList<>();
		try {
			books = service.getPendingBooks();
		}
		catch (BooksNotFound b) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserNotFound | NotEnoughPrivileges e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (books.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@RequestMapping(value = "/libros", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<List<BookDTO>> editBook(@RequestBody BookDTO book) {
		try {
			service.editBook(book.getId(), book.getState());
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (UserNotFound | NotEnoughPrivileges e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (BookNotFound b) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (NoRevisersAvailable r) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		} catch (BookNotEdited b) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
