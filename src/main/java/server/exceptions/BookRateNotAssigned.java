package server.exceptions;

@SuppressWarnings("serial")
public class BookRateNotAssigned extends RuntimeException {

	public BookRateNotAssigned() {
	}

	public BookRateNotAssigned(String message) {
		super(message);
	}
}
