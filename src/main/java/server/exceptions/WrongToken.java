package server.exceptions;

@SuppressWarnings("serial")
public class WrongToken extends RuntimeException {

	public WrongToken() {
	}

	public WrongToken(String message) {
		super(message);
	}
}
