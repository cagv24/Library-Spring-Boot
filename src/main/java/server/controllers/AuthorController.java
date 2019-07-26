package server.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import server.dto.BookDTO;
import server.exceptions.BookNotUploaded;
import server.exceptions.BooksNotFound;
import server.exceptions.NotEnoughPrivileges;
import server.exceptions.UserNotFound;
import server.exceptions.WrongParameters;
import server.service.LibraryService;

@RestController
@RequestMapping("/libreria/autor")
@CrossOrigin
public class AuthorController {

	@Autowired
	LibraryService service;

	@RequestMapping(value="{username}/libros", method = RequestMethod.POST, consumes="application/json")
	public ResponseEntity<HttpStatus> uploadBook(@PathVariable String username, @RequestBody BookDTO book) {
		try {
			service.uploadBook(book, username);
		} catch (UserNotFound | NotEnoughPrivileges e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (BookNotUploaded e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "{username}/libros", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<BookDTO>> getWrittenBooks(@PathVariable String username) {
		List<BookDTO> books = new ArrayList<>();
		try {
			books = service.getWrittenBooks(username);
		} catch (UserNotFound | NotEnoughPrivileges e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (BooksNotFound b) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

}
