package service;

import java.util.List;

import data.BookLoader;
import model.BookDTO;
import model.BookParameter;
import model.RentBook;

public interface BookService {
	List<BookDTO> getBooks(BookParameter parameter);
	void rentBook(RentBook rentBook);
	void returnRental(RentBook rentBook);

}
