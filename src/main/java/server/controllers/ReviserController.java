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
import server.exceptions.BookNotAssigned;
import server.exceptions.BookNotFound;
import server.exceptions.BookRateNotAssigned;
import server.exceptions.BooksNotFound;
import server.exceptions.NotEnoughPrivileges;
import server.exceptions.UserNotFound;
import server.exceptions.WrongParameters;
import server.service.LibraryService;

@RestController
@RequestMapping("libreria/revisor")
@CrossOrigin
public class ReviserController {

	@Autowired
	LibraryService service;
	
	@RequestMapping(value="{username}/libros", method = RequestMethod.PUT, consumes="application/json")
	public ResponseEntity<HttpStatus> rateBook(@PathVariable String username, @RequestBody BookDTO book) {	
		try {
			service.rateBook(book.getId(), book.getScore(), username);
		} catch (UserNotFound | NotEnoughPrivileges | BookNotAssigned e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (BookNotFound b) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (BookRateNotAssigned b) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="{username}/libros", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<List<BookDTO>> getBooksToCheck(@PathVariable String username) {
		List<BookDTO> books = new ArrayList<>();
		try {
			books = service.getBooksToCheck(username);
		} catch (BooksNotFound b) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (UserNotFound | NotEnoughPrivileges e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
}
