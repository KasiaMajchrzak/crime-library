package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import model.Book;
import model.BookParameter;
import utils.BookTestData;

class BookFilterTest {
	private BookFilter bookFilter = new BookFilter();

	@Test
	void shouldReturnBookIfNothingInSearchParameter() {
		Book book = BookTestData.getBook(1);

		boolean result = bookFilter.isValid(book, new BookParameter());

		assertThat(result).isTrue();
	}

	@ParameterizedTest(name = "Found a book for parameter {0}")
	@MethodSource("provideParameterWhichWillFindBook")
	void shouldReturnBook(BookParameter bookParameter) {
		Book book = BookTestData.getBook(1);

		boolean result = bookFilter.isValid(book, bookParameter);

		assertThat(result).isTrue();
	}

	@ParameterizedTest(name = "Did not find a book for parameter {0}")
	@MethodSource("provideParametersWhichWillNotFindBook")
	void shouldNotReturnBook(BookParameter bookParameter) {
		Book book = BookTestData.getBook(1);

		boolean result = bookFilter.isValid(book, bookParameter);

		assertThat(result).isFalse();
	}

	private static Stream<Arguments> provideParameterWhichWillFindBook() {
		return Stream.of(
				Arguments.of(new BookParameter().setAuthor("Stephen King")),
				Arguments.of(new BookParameter().setTitle("Mr. Mercedes")),
				Arguments.of(new BookParameter().setYear(2012)),
				Arguments.of(new BookParameter().setPrice(2.5)),
				Arguments.of(new BookParameter().setAuthor("Stephen King").setTitle("Mr. Mercedes")),
				Arguments.of(new BookParameter().setAuthor("Stephen King").setTitle("Mr. Mercedes").setYear(2012)),
				Arguments.of(new BookParameter().setAuthor("Stephen King").setTitle("Mr. Mercedes").setYear(2012).setPrice(2.5))
		);
	}

	private static Stream<Arguments> provideParametersWhichWillNotFindBook() {
		return Stream.of(
				Arguments.of(new BookParameter().setAuthor("stephen king")),
				Arguments.of(new BookParameter().setTitle("mr. mercedes")),
				Arguments.of(new BookParameter().setPrice(1.0)),
				Arguments.of(new BookParameter().setYear(2019))
		);
	}

}