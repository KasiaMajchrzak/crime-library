package data;

import java.util.Collection;
import java.util.Optional;

import model.Book;
import model.RentBook;
import model.TimeRange;

public class BookRepositoriesFacade {
	private final BookRepository bookRepository;
	private final RentBookRepository rentBookRepository;

	public BookRepositoriesFacade(BookRepository bookRepository, RentBookRepository rentBookRepository) {
		this.bookRepository = bookRepository;
		this.rentBookRepository = rentBookRepository;
	}

	public static BookRepositoriesFacade newInstance(BookRepository bookRepository) {
		return new BookRepositoriesFacade(bookRepository, new RentBookRepository());
	}

	public Collection<Book> getBooks() {
		return bookRepository.getBooks();
	}

	public void addRentBook(RentBook rentBook) {
		rentBookRepository.addRentBook(rentBook);
	}

	public Optional<Book> findBookById(long bookId) {
		return bookRepository.findById(bookId);
	}

	public Optional<RentBook> findRent(long bookId, TimeRange timeRange) {
		return rentBookRepository.findRent(bookId, timeRange);
	}

	public boolean returnRental(RentBook rentBook) {
		return rentBookRepository.returnRental(rentBook);
	}

	public int howManyRentals(){
		return rentBookRepository.size();
	}
}
