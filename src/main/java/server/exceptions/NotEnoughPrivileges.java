package server.exceptions;

@SuppressWarnings("serial")
public class NotEnoughPrivileges extends RuntimeException {

	public NotEnoughPrivileges() {
	}

	public NotEnoughPrivileges(String message) {
		super(message);
	}

}
