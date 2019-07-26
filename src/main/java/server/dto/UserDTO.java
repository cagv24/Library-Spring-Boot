package server.dto;


public class UserDTO {
	private String username;
	private String password;
	private int userType;
	private String email;
	private String name;
	private String lastName;
	private String tel;
	private int age;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(String username, String password, int userType, String email, String name, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
