package server.dto;

import java.util.Date;

public class BookDTO {

	private long id;
	private String name;
	private String genre;
	private String format;
	private String autor;
	private Date publicationDate;
	private int score;
	private int state;

	public BookDTO() {
		super();
	}
	
	public BookDTO(long id, String name, String genre, String format, String autor, Date publicationDate, int state) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.format = format;
		this.autor = autor;
		this.publicationDate = publicationDate;
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
}
