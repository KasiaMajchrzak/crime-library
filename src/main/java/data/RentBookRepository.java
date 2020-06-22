package data;

import java.sql.Time;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import model.RentBook;
import model.TimeRange;

public class RentBookRepository {
	private Set<RentBook> rentBooks = new HashSet<>();

	void addRentBook(RentBook rentBook) {
		if(rentBook == null) {
			throw new IllegalArgumentException("Rent book cannot be null");
		}
		rentBooks.add(rentBook);
	}

	int size() {
		return rentBooks.size();
	}

	Optional<RentBook> findRent(long bookId, TimeRange timeRange) {
		return rentBooks.stream().filter(book -> book.getBookId() == bookId)
				.filter(book -> !(timeRange.getTo().isBefore(book.getFrom()) || timeRange.getFrom().isAfter(book.getTo()))).findAny();
	}

	void reset() {
		rentBooks.clear();
	}

	boolean returnRental(RentBook rentBook) {
		return rentBooks.remove(rentBook);
	}
}
