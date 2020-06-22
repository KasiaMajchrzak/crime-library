package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import data.BookRepositoriesFacade;
import model.RentBook;

@ExtendWith(MockitoExtension.class)
class ValidatorFacadeTest {

	@InjectMocks
	private ValidatorFacade facade;

	@Spy
	private TimeRangeParameterValidator timeRangeParameterValidator;

	@Mock
	private RentBookValidator rentBookValidator;

	@Test
	void shouldValidateBookParameterAsInvalid() {

		List<String> errors = facade.isValid(null);

		assertThat(errors).contains(TimeRangeParameterValidator.PARAMETER_CANNOT_BE_NULL);
	}

	@Test
	void shouldValidateRentBookAsInvalidSinceNoTimeRange() {
		RentBook rentBook = new RentBook(1, null, null);
		BookRepositoriesFacade bookRepositoriesFacade = mock(BookRepositoriesFacade.class);

		List<String> errors = facade.isValid(rentBook, bookRepositoriesFacade);

		assertThat(errors).contains(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET);
	}

	@Test
	void shouldReturnRentBookAsInvalidIfItIsRented() {
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2020, 6, 10, 10, 0));
		BookRepositoriesFacade bookRepositoriesFacade = mock(BookRepositoriesFacade.class);
		given(rentBookValidator.isValid(any(RentBook.class), any(BookRepositoriesFacade.class)))
				.willReturn(Collections.singletonList(RentBookValidator.BOOK_WITH_THIS_ID_IS_NOT_AVAILABLE));

		List<String> errors = facade.isValid(rentBook, bookRepositoriesFacade);

		assertThat(errors).contains(RentBookValidator.BOOK_WITH_THIS_ID_IS_NOT_AVAILABLE);
	}

}