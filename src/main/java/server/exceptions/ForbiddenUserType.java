package server.exceptions;

@SuppressWarnings("serial")
public class ForbiddenUserType extends RuntimeException {

	public ForbiddenUserType() {
	}

	public ForbiddenUserType(String message) {
		super(message);
	}


}
