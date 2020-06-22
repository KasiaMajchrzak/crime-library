package service;

import java.util.ArrayList;
import java.util.List;

import model.TimeRange;

public class TimeRangeParameterValidator {
	static final String PARAMETER_CANNOT_BE_NULL = "Parameter cannot be null";
	static final String TIMERANGE_MUST_BE_SET = "From and to must be set";
	static final String TIMERANGE_INVALID = "To must be greater than from";

	public List<String> isValid(TimeRange timeRange){
		List<String> errors = new ArrayList<>();

		if(timeRange == null) {
			errors.add(PARAMETER_CANNOT_BE_NULL);
		} else if (timeRange.getFrom() == null || timeRange.getTo() == null) {
			errors.add(TIMERANGE_MUST_BE_SET);
		} else if (timeRange.getFrom().equals(timeRange.getTo()) || timeRange.getFrom().isAfter(timeRange.getTo())) {
			errors.add(TIMERANGE_INVALID);
		}

		return errors;
	}

}
