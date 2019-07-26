package server.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import server.utils.BookState;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Version
	private Integer version;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String genre;

	@Column(nullable = false)
	private int state;

	private String format;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "scores")
	private List<Integer> score;

	@Column(nullable = false)
	private String autor;

	@Temporal(TemporalType.DATE)
	private Date publicationDate;

	public Book() {
		super();
	}

	public Book(String name, String genre, String format, String autor, Date publicationDate) {
		super();
		this.name = name;
		this.genre = genre;
		this.state = BookState.UPLOADED.getState();
		this.format = format;
		this.score = new ArrayList<Integer>();
		this.autor = autor;
		this.publicationDate = publicationDate;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<Integer> getScore() {
		return score;
	}

	public void setScore(List<Integer> score) {
		this.score = score;
	}

	public void addScore(int score) {
		this.score.add(score);				
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
}
