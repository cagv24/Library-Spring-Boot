package server.controllers;

import server.dto.BookDTO;
import server.exceptions.BooksNotFound;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import server.service.LibraryService;

@RestController
@RequestMapping("/libreria")
@CrossOrigin
public class LibraryController {

	@Autowired
	LibraryService service;

	@RequestMapping(value = "/libros", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String genero) {
		List<BookDTO> books = new ArrayList<>();
		try {
			if (nombre != null)
				books = service.getBooksByName(nombre);
			else if (genero != null)
				books = service.getBooksByGenre(genero);
		} catch (BooksNotFound e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(books, HttpStatus.OK);
	}
}
