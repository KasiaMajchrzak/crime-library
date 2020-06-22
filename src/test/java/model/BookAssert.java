package model;

import org.assertj.core.api.AbstractAssert;

import java.util.Objects;

public class BookAssert extends AbstractAssert<BookAssert, Book> {
	private BookAssert(Book book) {super(book, BookAssert.class);}

	public static BookAssert assertThat(Book actual){ return new BookAssert(actual); }

	public BookAssert hasId(long id) {
		isNotNull();

		if(actual.getId() != id) {
			failWithMessage("Expected book's id to be <%s> but was <%s>", id, actual.getId());
		}
		return this;
	}

	public BookAssert hasAuthor(String author){
		isNotNull();

		if(!Objects.equals(actual.getAuthor(), author)) {
			failWithMessage("Expected book's author to be <%s> but was <%s>", author, actual.getAuthor());
		}

		return this;
	}

	public BookAssert hasTitle(String title){
		isNotNull();

		if(!Objects.equals(actual.getTitle(), title)) {
			failWithMessage("Expected book's title to be <%s> but was <%s>", title, actual.getTitle());
		}

		return this;
	}

	public BookAssert hasYear(int year){
		isNotNull();

		if(actual.getYear() != year) {
			failWithMessage("Expected book's year to be <%s> but was <%s>", year, actual.getYear());
		}

		return this;
	}

	public BookAssert hasPrice(double price){
		isNotNull();

		if(actual.getPrice() != price) {
			failWithMessage("Expected book's price to be <%s> but was <%s>", price, actual.getPrice());
		}

		return this;
	}
}
