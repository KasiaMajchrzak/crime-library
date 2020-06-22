package utils;

import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.BookBuilder;

public class BookTestData {
	private static final List<Book> BOOKS = initBooks();

	private static List<Book> initBooks() {
		List<Book> books = new ArrayList<>();
		BookBuilder bookBuilder = new BookBuilder();

		bookBuilder.setId(1)
				.setAuthor("Stephen King")
				.setTitle("Mr. Mercedes")
				.setYear(2010)
				.setPrice(2.0);
		books.add(bookBuilder.createBook());
		bookBuilder.setId(2)
				.setYear(2012)
				.setPrice(2.5);
		books.add(bookBuilder.createBook());

		return books;
	}

	public static List<Book> getBooks(int from, int to) {
		return BOOKS.subList(from, to);
	}

	public static Book getBook(int index) {
		return BOOKS.get(index);
	}

}
