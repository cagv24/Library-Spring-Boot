package server.exceptions;

@SuppressWarnings("serial")
public class BookNotEdited extends RuntimeException {

	public BookNotEdited() {
	}

	public BookNotEdited(String message) {
		super(message);
	}
}
