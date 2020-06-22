package data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import model.RentBook;

class RentBookRepositoryTest {
	RentBookRepository rentBookRepository = new RentBookRepository();

	@Test
	void shouldAddRentCar() {
		rentBookRepository.reset();
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2019, 6, 8, 10, 0));

		rentBookRepository.addRentBook(rentBook);

		assertThat(rentBookRepository.size()).isEqualTo(1);
	}

	@Test
	void shouldThrowExceptionIfRentBookIsNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> rentBookRepository.addRentBook(null)).withMessage("Rent book cannot be null");
	}

	@ParameterizedTest(name = "No previous rent for rent {0}")
	@MethodSource("provideRentsWhichNotExistsInRepo")
	void shouldNotFindRentSinceNewRentInDifferentPeriod(RentBook newRentBook) {
		rentBookRepository.reset();
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 4, 10, 0), LocalDateTime.of(2020, 6, 5, 10, 0));
		rentBookRepository.addRentBook(rentBook);
		rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 14, 10, 0), LocalDateTime.of(2020, 6, 15, 10, 0));
		rentBookRepository.addRentBook(rentBook);

		Optional<RentBook> activeRent = rentBookRepository.findRent(newRentBook.getBookId(), newRentBook);

		assertThat(activeRent.isPresent()).isFalse();
	}

	@ParameterizedTest(name = "Previous rent for rent {0}")
	@MethodSource("provideRentsWhichExistsInRepo")
	void shouldFindRentSinceNewRentInSimilarPeriod(RentBook newRentBook) {
		rentBookRepository.reset();
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 4, 10, 0), LocalDateTime.of(2020, 6, 5, 10, 0));
		rentBookRepository.addRentBook(rentBook);
		rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 14, 10, 0), LocalDateTime.of(2020, 6, 15, 10, 0));
		rentBookRepository.addRentBook(rentBook);

		Optional<RentBook> activeRent = rentBookRepository.findRent(newRentBook.getBookId(), newRentBook);

		assertThat(activeRent.isPresent()).isTrue();
	}

	@Test
	void shouldNotThrowExceptionIfTryingToReturnABookWhichWasNotRented() {
		rentBookRepository.reset();
		RentBook rentBook = new RentBook(1, LocalDateTime.of(2020, 6, 1, 10, 0), LocalDateTime.of(2019, 6, 8, 10, 0));

		boolean wasReturned = rentBookRepository.returnRental(rentBook);

		assertThat(wasReturned).isFalse();
	}

	private static Stream<Arguments> provideRentsWhichNotExistsInRepo() {
		return Stream.of(
				Arguments.of(new RentBook(2, LocalDateTime.of(2020, 6, 4, 11, 0), LocalDateTime.of(2020, 6, 5, 11, 0))),
				Arguments.of(new RentBook(2, LocalDateTime.of(2020, 6, 14, 11, 0), LocalDateTime.of(2020, 6, 15, 11, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 16, 11, 0), LocalDateTime.of(2020, 6, 18, 11, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 1, 11, 0), LocalDateTime.of(2020, 6, 3, 11, 0)))

		);
	}

	private static Stream<Arguments> provideRentsWhichExistsInRepo() {
		return Stream.of(
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 4, 10, 0), LocalDateTime.of(2020, 6, 5, 10, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 4, 10, 0), LocalDateTime.of(2020, 6, 15, 10, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 4, 10, 0), LocalDateTime.of(2020, 6, 4, 10, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 5, 10, 0), LocalDateTime.of(2020, 6, 6, 10, 0))),
				Arguments.of(new RentBook(1, LocalDateTime.of(2020, 6, 5, 10, 0), LocalDateTime.of(2020, 6, 8, 10, 0)))

		);
	}
}