package service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import model.BookParameter;

class TimeRangeParameterValidatorTest {

	private TimeRangeParameterValidator validator = new TimeRangeParameterValidator();

	@Test
	void shouldValidateParameterAsIncorrectSinceNull() {

		List<String> result = validator.isValid(null);

		assertThat(result).contains(TimeRangeParameterValidator.PARAMETER_CANNOT_BE_NULL);
	}

	@Test
	void shouldValidateParameterAsIncorrectSinceEmpty() {

		List<String> result = validator.isValid(new BookParameter());

		assertThat(result).contains(TimeRangeParameterValidator.TIMERANGE_MUST_BE_SET);
	}

	@Test
	void shouldValidateParameterAsIncorrectSinceToAndFromAreTheSame() {
		LocalDateTime from = LocalDateTime.now();
		List<String> result = validator.isValid(new BookParameter().setFrom(from).setTo(from));

		assertThat(result).contains(TimeRangeParameterValidator.TIMERANGE_INVALID);
	}

	@Test
	void shouldValidateParameterAsIncorrectSinceFromIsAfterTo() {
		LocalDateTime to = LocalDateTime.now();
		List<String> result = validator.isValid(new BookParameter().setFrom(to.plusDays(2)).setTo(to));

		assertThat(result).contains(TimeRangeParameterValidator.TIMERANGE_INVALID);
	}

	@Test
	void shouldValidareParameterAsCorrect() {
		LocalDateTime from = LocalDateTime.now();
		List<String> result = validator.isValid(new BookParameter().setFrom(from).setTo(from.plusDays(2)));

		assertThat(result).isEmpty();
	}
}