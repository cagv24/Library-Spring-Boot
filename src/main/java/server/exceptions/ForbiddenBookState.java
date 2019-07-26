package server.exceptions;

@SuppressWarnings("serial")
public class ForbiddenBookState extends RuntimeException {

	public ForbiddenBookState() {
	}

	public ForbiddenBookState(String message) {
		super(message);
	}

}
