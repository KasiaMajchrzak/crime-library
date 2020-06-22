package service;

import java.util.List;

import data.BookRepositoriesFacade;
import model.RentBook;
import model.TimeRange;

public class ValidatorFacade {
	private final RentBookValidator rentBookValidator;
	private final TimeRangeParameterValidator timeRangeParameterValidator;


	public ValidatorFacade(RentBookValidator rentBookValidator, TimeRangeParameterValidator timeRangeParameterValidator) {
		this.rentBookValidator = rentBookValidator;
		this.timeRangeParameterValidator = timeRangeParameterValidator;
	}

	public static ValidatorFacade newInstance() {
		return new ValidatorFacade(new RentBookValidator(), new TimeRangeParameterValidator());
	}

	public List<String> isValid(TimeRange timeRange) {
		return timeRangeParameterValidator.isValid(timeRange);
	}

	public List<String> isValid(RentBook rentBook, BookRepositoriesFacade bookRepositoriesFacade) {
		List<String> errors = timeRangeParameterValidator.isValid(rentBook);
		if(errors.isEmpty()) {
			errors.addAll(rentBookValidator.isValid(rentBook, bookRepositoriesFacade));
		}
		return errors;
	}
}
