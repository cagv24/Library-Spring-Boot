package server.exceptions;

@SuppressWarnings("serial")
public class UserNotFound extends RuntimeException {

	public UserNotFound() {
	}

	public UserNotFound(String message) {
		super(message);
	}
}
