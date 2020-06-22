package data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookLoaderTest {

	private BookLoader bookLoader = new BookLoader();

	@Test
	void shouldLoadBooksFromFile() {
		BookRepository bookRepository = new BookRepository();

		bookLoader.load("books.json", bookRepository);

		assertThat(bookRepository.getBooks().size()).isEqualTo(5);
	}

	@Test
	void shouldNotLoadBooksIfInvalidFile() {
		BookRepository bookRepository = new BookRepository();

		bookLoader.load("books-invalid.json", bookRepository);

		assertThat(bookRepository.getBooks().size()).isEqualTo(0);
	}
}