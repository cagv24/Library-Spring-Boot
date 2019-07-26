package server.exceptions;

@SuppressWarnings("serial")
public class WrongParameters extends RuntimeException {

	public WrongParameters() {
	}

	public WrongParameters(String message) {
		super(message);
	}

}
