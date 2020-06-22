package data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import model.Book;
import utils.BookTestData;

class BookRepositoryTest {
	private BookRepository bookRepository = new BookRepository();

	@Test
	void shouldFindBooksByTitle() {
		bookRepository.update(BookTestData.getBooks(0,2));

		Optional<Book> book = bookRepository.findByTitle("Mr. Mercedes");

		assertThat(book.isPresent()).isTrue();
	}

}