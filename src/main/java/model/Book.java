package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BookBuilder.class)
public class Book {
	private final long id;
	private final String author;
	private final String title;
	private final int year;
	private final double price;

	Book(long id, String author, String title, int year, double price) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.year = year;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public double getPrice() {
		return price;
	}
}
