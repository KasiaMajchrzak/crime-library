package data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import model.Book;
import model.RentBook;
import utils.BookTestData;


@ExtendWith(MockitoExtension.class)
class BookRepositoriesFacadeTest {

	@InjectMocks
	private BookRepositoriesFacade bookRepositoriesFacade;

	@Mock
	private BookRepository bookRepository;

	@Spy
	private RentBookRepository rentBookRepository;

	@Test
	void shouldListBooks() {
		given(bookRepository.getBooks()).willReturn(BookTestData.getBooks(0, 2));

		Collection<Book> books = bookRepositoriesFacade.getBooks();

		assertThat(books).hasSize(2);
	}

	@Test
	void shouldAddBookToRentRepositories() {
		assertThat(rentBookRepository.size()).isEqualTo(0);
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 2, 10, 0));

		bookRepositoriesFacade.addRentBook(rentBook);

		assertThat(rentBookRepository.size()).isEqualTo(1);
		rentBookRepository.reset();
	}

	@Test
	void shouldNotFindBookBasedOnId() {
		long bookId = 1;
		given(bookRepository.findById(bookId)).willReturn(Optional.empty());

		Optional<Book> book = bookRepositoriesFacade.findBookById(bookId);

		assertThat(book.isPresent()).isFalse();
	}

	@Test
	void shouldNotFindRent() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 2, 10, 0));

		Optional<RentBook> activeRent = bookRepositoriesFacade.findRent(rentBook.getBookId(), rentBook);

		assertThat(activeRent.isPresent()).isFalse();
	}

	@Test
	void shouldReturnBook() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 2, 10, 0));
		bookRepositoriesFacade.addRentBook(rentBook);
		assertThat(rentBookRepository.findRent(rentBook.getBookId(), rentBook).isPresent()).isTrue();

		bookRepositoriesFacade.returnRental(new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 2, 10, 0)));

		assertThat(rentBookRepository.findRent(rentBook.getBookId(), rentBook).isPresent()).isFalse();
	}
}