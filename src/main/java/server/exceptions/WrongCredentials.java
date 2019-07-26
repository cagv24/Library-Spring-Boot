package server.exceptions;

@SuppressWarnings("serial")
public class WrongCredentials extends RuntimeException {

	public WrongCredentials() {
	}

	public WrongCredentials(String message) {
		super(message);
	}
}
