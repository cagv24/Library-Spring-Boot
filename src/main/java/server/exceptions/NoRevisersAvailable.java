package server.exceptions;

@SuppressWarnings("serial")
public class NoRevisersAvailable extends RuntimeException {

	public NoRevisersAvailable() {
	}

	public NoRevisersAvailable(String message) {
		super(message);
	}
}
