package model;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.commons.lang3.StringUtils;

@JsonPOJOBuilder(buildMethodName = "createBook", withPrefix = "set")
public class BookBuilder {
	private long id;
	private String author;
	private String title;
	private int year;
	private double price;

	public BookBuilder setId (long id) {
		this.id = id;
		return this;
	}

	public BookBuilder setAuthor(String author) {
		this.author = author;
		return this;
	}

	public BookBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public BookBuilder setYear(int year) {
		this.year = year;
		return this;
	}

	public BookBuilder setPrice(double price) {
		this.price = price;
		return this;
	}

	public Book createBook() {
		if(id == 0) {
			throw new IllegalArgumentException("Id must be set");
		}

		if(StringUtils.isAnyBlank(title, author)) {
			throw new IllegalArgumentException("Required attributes must be set");
		}

		if(price<=0){
			throw new IllegalArgumentException("Price must be set to a value greater than 0");
		}

		Book book = new Book(id, author, title, year, price);
		return book;
	}
}
