package server.exceptions;

@SuppressWarnings("serial")
public class UsernameEmpty extends RuntimeException {

	public UsernameEmpty() {
	}

	public UsernameEmpty(String message) {
		super(message);
	}
}
