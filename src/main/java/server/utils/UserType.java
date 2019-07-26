package server.utils;

public enum UserType {
	
	AUTHOR("Author", 1),
	REVISER("Reviser", 2),
	EDITOR("Editor", 3);
	
	private final String name;
	private final int id;
	private String username;
	
	UserType(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
        return name;
    }
	
    public int getId() {
        return id;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }
}