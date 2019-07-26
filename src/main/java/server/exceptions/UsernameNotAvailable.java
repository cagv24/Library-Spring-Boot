package server.exceptions;

@SuppressWarnings("serial")
public class UsernameNotAvailable extends Exception {

	public UsernameNotAvailable() {
	}

	public UsernameNotAvailable(String message) {
		super(message);
	}

}
