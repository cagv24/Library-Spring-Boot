package server.exceptions;

@SuppressWarnings("serial")
public class ForbiddenBook extends RuntimeException {

	public ForbiddenBook() {
	}

	public ForbiddenBook(String message) {
		super(message);
	}
}
