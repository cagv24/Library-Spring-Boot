package server.exceptions;

@SuppressWarnings("serial")
public class BookNotAssigned extends RuntimeException {

	public BookNotAssigned() {
	}

	public BookNotAssigned(String message) {
		super(message);
	}
}
