package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import data.BookRepositoriesFacade;
import model.BookDTO;
import model.BookParameter;
import model.RentBook;
import utils.BookTestData;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepositoriesFacade bookRepositoriesFacade;

	@Spy
	private BookFilter bookFilter;

	@Mock
	private ValidatorFacade validator;

	private LocalDateTime from = LocalDateTime.now();

	@Test
	void shouldReturnAListOf2Books() {
		given(bookRepositoriesFacade.getBooks()).willReturn(BookTestData.getBooks(0, 2));
		BookParameter bookParameter = new BookParameter().setFrom(from).setTo(from.plusDays(2));
		given(validator.isValid(bookParameter)).willReturn(new ArrayList<>());

		List<BookDTO> result = bookService.getBooks(bookParameter);
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getPrice()).isEqualTo(4.0);
	}

	@Test
	void shouldReturnListOfOneBook() {
		given(bookRepositoriesFacade.getBooks()).willReturn(BookTestData.getBooks(0, 2));
		BookParameter bookParameter = new BookParameter().setFrom(from).setTo(from.plusDays(2)).setYear(2010);
		given(validator.isValid(bookParameter)).willReturn(new ArrayList<>());

		List<BookDTO> result = bookService.getBooks(bookParameter);

		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnAnEmptyListWhenAllBooksAreRented() {
		given(bookRepositoriesFacade.getBooks()).willReturn(BookTestData.getBooks(0, 2));
		BookParameter bookParameter = new BookParameter().setFrom(from).setTo(from.plusDays(2)).setYear(2010);
		given(validator.isValid(bookParameter)).willReturn(new ArrayList<>());
		RentBook rentBook = mock(RentBook.class);
		given(bookRepositoriesFacade.findRent(anyLong(), any(BookParameter.class))).willReturn(Optional.of(rentBook));

		List<BookDTO> result = bookService.getBooks(bookParameter);

		assertThat(result.size()).isEqualTo(0);
	}

	@Test
	void shouldThrowExceptionIfInvalidParameter() {
		BookParameter bookParameter = new BookParameter();
		given(validator.isValid(bookParameter)).willReturn(Collections.singletonList(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET));

		assertThatIllegalArgumentException().isThrownBy(() -> bookService.getBooks(bookParameter))
				.withMessage(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET);
	}

	@Test
	void shouldAddRentBookToRepository() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 8, 10, 0));

		bookService.rentBook(rentBook);

		verify(bookRepositoriesFacade).addRentBook(rentBook);
	}

	@Test
	void shouldThrowExceptionIfInvalidParameterWhileRentingBook() {
		RentBook rentBook = new RentBook(1, null, null);
		given(validator.isValid(rentBook, bookRepositoriesFacade)).willReturn(Collections.singletonList(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET));

		assertThatIllegalArgumentException().isThrownBy(() -> bookService.rentBook(rentBook))
				.withMessage(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET);
	}

	@Test
	void shouldReturnBook() {
		RentBook rentBook = new RentBook(1, null, null);
		given(bookRepositoriesFacade.returnRental(rentBook)).willReturn(true);

		bookService.returnRental(rentBook);

		verify(bookRepositoriesFacade).returnRental(rentBook);
	}
}