package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

class BookParameterTest {

	@ParameterizedTest(name = "Days for parameter {0}")
	@MethodSource("bookParameterProvider")
	void shouldReturnCorrectNumberOfDays(BookParameter parameter, long expectedDays) {
		long days = parameter.howManyDays();

		assertThat(days).isEqualTo(expectedDays);
	}


	private static Stream<Arguments> bookParameterProvider() {
		return Stream.of(
				Arguments.of(new BookParameter()
						.setFrom(LocalDateTime.of(2020, 6, 1, 10, 0))
						.setTo(LocalDateTime.of(2020, 6, 10, 10, 0)), 9),
				Arguments.of(new BookParameter()
						.setFrom(LocalDateTime.of(2020, 6, 1, 10, 0))
						.setTo(LocalDateTime.of(2020, 6, 10, 18, 0)), 10),
				Arguments.of(new BookParameter()
						.setFrom(LocalDateTime.of(2020, 6, 1, 10, 10))
						.setTo(LocalDateTime.of(2020, 6, 10, 10, 10)), 9),
				Arguments.of(new BookParameter()
						.setFrom(LocalDateTime.of(2020, 6, 1, 10, 0))
						.setTo(LocalDateTime.of(2020, 6, 1, 18, 0)), 1)
		);
	}

}