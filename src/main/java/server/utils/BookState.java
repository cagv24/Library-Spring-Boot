package server.utils;

public enum BookState {
	UPLOADED("Uploaded", 1),
	REVIEW("In Review", 2),
	PUBLISHED("Published", 3),
	DISCARDED("Discarded", 4);
	
	private final String name;
	private final int state;
	
	BookState(String name, int state) {
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public int getState() {
		return state;
	}
	
}
