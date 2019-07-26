package server.exceptions;

@SuppressWarnings("serial")
public class UserNotCreated extends RuntimeException {

	public UserNotCreated() {
	}

	public UserNotCreated(String message) {
		super(message);
	}
}
