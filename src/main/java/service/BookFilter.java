package service;

import org.apache.commons.lang3.StringUtils;

import java.util.function.BiPredicate;

import model.Book;
import model.BookParameter;

public class BookFilter {
	private static final BiPredicate<String, String> CONDITION = (parameterValue, bookValue) -> (StringUtils.isBlank(parameterValue) ||
			(StringUtils.equals(parameterValue, bookValue)));

	public boolean isValid(Book book, BookParameter bookParameter) {

		return CONDITION.test(bookParameter.getAuthor(), book.getAuthor())
				&& CONDITION.test(bookParameter.getTitle(), book.getTitle())
				&& (bookParameter.getYear() == book.getYear() || bookParameter.getYear() == 0)
				&& (bookParameter.getPrice() == book.getPrice() || bookParameter.getPrice() == 0);
	}
}
