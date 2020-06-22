package service;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import data.BookRepositoriesFacade;
import model.RentBook;

public class RentBookValidator {
	static final String BOOK_WITH_THIS_ID_IS_NOT_AVAILABLE = "Book with this id is not available";
	static final String THIS_BOOK_IS_RENTED_IN_THIS_PERIOD = "This book is rented in this period";
	static final String RENT_OK = "";

	public List<String> isValid(RentBook rentBook, BookRepositoriesFacade bookRepositoriesFacade) {
		List<String> errors = new ArrayList<>();
		String error = bookRepositoriesFacade.findBookById(rentBook.getBookId())
				.map(book -> checkRent(rentBook, bookRepositoriesFacade))
				.orElse(BOOK_WITH_THIS_ID_IS_NOT_AVAILABLE);

		if(StringUtils.isNotBlank(error)) {
			errors.add(error);
		}
		return errors;
	}

	private String checkRent(RentBook rentBook, BookRepositoriesFacade bookRepositoriesFacade) {
		return bookRepositoriesFacade.findRent(rentBook.getBookId(), rentBook)
				.map(rent -> THIS_BOOK_IS_RENTED_IN_THIS_PERIOD).orElse(RENT_OK);
	}
}
