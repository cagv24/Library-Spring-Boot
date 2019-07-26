package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import server.dto.UserDTO;
import server.exceptions.UserNotCreated;
import server.exceptions.WrongParameters;
import server.service.LibraryService;

@RestController
@RequestMapping("/libreria/usuario")
@CrossOrigin()
public class UserController {

	@Autowired
	LibraryService service;
	
	@RequestMapping(value="{username}/login", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<UserDTO> logIn(@PathVariable String username) {		
			
			UserDTO user = service.getUserByUsername(username);
		    return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/signin", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public ResponseEntity<UserDTO> signIn(@RequestBody UserDTO user) {		
		try {
			UserDTO userDTO = service.signIn(user);
		    return new ResponseEntity<>(userDTO, HttpStatus.OK);
		} catch (WrongParameters p) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (UserNotCreated u) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
