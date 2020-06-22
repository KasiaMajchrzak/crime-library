package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import data.BookRepositoriesFacade;
import model.RentBook;
import utils.BookTestData;

@ExtendWith(MockitoExtension.class)
class RentBookValidatorTest {

	private RentBookValidator rentBookValidator = new RentBookValidator();

	@Mock
	private BookRepositoriesFacade facade;

	@Test
	void shouldValidateRentBookIsInvalidSinceNoSuchBook() {
		RentBook rentBook = new RentBook(3, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 10, 10, 0));
		given(facade.findBookById(3)).willReturn(Optional.empty());

		List<String> errors = rentBookValidator.isValid(rentBook, facade);

		assertThat(errors).contains(RentBookValidator.BOOK_WITH_THIS_ID_IS_NOT_AVAILABLE);
	}

	@Test
	void shouldValidateRentBookAsInvalidWhenItIsRented() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 10, 10, 0));
		given(facade.findBookById(1)).willReturn(Optional.of(BookTestData.getBook(0)));
		given(facade.findRent(1, rentBook)).willReturn(Optional.of(rentBook));

		List<String> errors = rentBookValidator.isValid(rentBook, facade);

		assertThat(errors).contains(RentBookValidator.THIS_BOOK_IS_RENTED_IN_THIS_PERIOD);
	}

	@Test
	void shouldValidateRentBookAsCorrect() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 10, 10, 0));
		given(facade.findBookById(1)).willReturn(Optional.of(BookTestData.getBook(0)));
		given(facade.findRent(1, rentBook)).willReturn(Optional.empty());

		List<String> errors = rentBookValidator.isValid(rentBook, facade);

		assertThat(errors).isEmpty();
	}

}