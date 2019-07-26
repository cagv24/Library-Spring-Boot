package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import server.dao.UserDAO;

@Component
public class UserService implements UserDetailsService {

	@Autowired
	UserDAO userDAO; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			server.entities.User user = userDAO.getUser(username);
			String role = "";
			switch(user.getUserType()) {
				case 1: {
					role = "AUTHOR";
					break;
				}
				case 2: {
					role = "REVISER";
					break;
				}
				case 3: {
					role = "EDITOR";
					break;
				}
			}
			return User.withUsername(username).password(user.getPassword()).roles(role).build();
	}
}
