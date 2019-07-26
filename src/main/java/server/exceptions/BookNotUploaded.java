package server.exceptions;

@SuppressWarnings("serial")
public class BookNotUploaded extends RuntimeException {

	public BookNotUploaded() {
	}

	public BookNotUploaded(String message) {
		super(message);
	}
}
