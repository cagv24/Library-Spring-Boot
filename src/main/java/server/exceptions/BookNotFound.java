package server.exceptions;

@SuppressWarnings("serial")
public class BookNotFound extends RuntimeException {

	public BookNotFound() {
	}

	public BookNotFound(String message) {
		super(message);
	}
}
