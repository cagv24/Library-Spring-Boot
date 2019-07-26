package server.exceptions;

@SuppressWarnings("serial")
public class BooksNotFound extends RuntimeException {

	public BooksNotFound() {
		super();
	}

	public BooksNotFound(String message) {
		super(message);
	}

}
