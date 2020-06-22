package data;

import java.util.*;

import model.Book;

public class BookRepository {
	private final Collection<Book> books = new HashSet<>();

	Collection<Book> getBooks() {
		return Collections.unmodifiableCollection(books);
	}

	void update(List<Book> books) {
		this.books.addAll(books);
	}

	Optional<Book> findByTitle(String bookTitle) {
		return books.stream().filter(book -> book.getTitle().equals(bookTitle)).findAny();
	}

	public Optional<Book> findById(long bookId) {
		return books.stream().filter(book -> book.getId() == bookId).findAny();
	}
}
