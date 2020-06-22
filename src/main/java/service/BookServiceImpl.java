package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import data.BookRepositoriesFacade;
import model.BookDTO;
import model.BookParameter;
import model.RentBook;

public class BookServiceImpl  implements BookService{
	private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);
	protected final ReadWriteLock lock = new ReentrantReadWriteLock();
	protected final BookRepositoriesFacade bookRepositoriesFacade;
	protected final ValidatorFacade validatorFacade;
	private final BookFilter bookFilter;

	public BookServiceImpl(BookRepositoriesFacade bookRepositoriesFacade, ValidatorFacade validatorFacade, BookFilter bookFilter) {
		this.bookRepositoriesFacade = bookRepositoriesFacade;
		this.validatorFacade = validatorFacade;
		this.bookFilter = bookFilter;
	}


	@Override
	public List<BookDTO> getBooks(BookParameter parameter) {
		LOG.info("entering getBooks({})", parameter);
		List<BookDTO> result = new ArrayList<>();

		try {
			lock.readLock().lock();
			List<String> errors = validatorFacade.isValid(parameter);

			if(!errors.isEmpty()) {
				throw new IllegalArgumentException(getAllErrors(errors));
			}

			result.addAll(bookRepositoriesFacade.getBooks().stream()
			.filter(book -> bookFilter.isValid(book, parameter))
			.filter(book -> bookRepositoriesFacade.findRent(book.getId(), parameter).isEmpty())
			.map(book -> new BookDTO(book, parameter.howManyDays())).collect(Collectors.toList()));

			LOG.info("finished getBooks({}), found {} books", parameter, result.size());
			return result;
		} finally {
			lock.readLock().unlock();
		}
	}

	protected String getAllErrors(List<String> errors) { return String.join(",", errors); }

	@Override
	public void rentBook(RentBook rentBook) {
		LOG.info("entering rentBook({})", rentBook);

		try {
				lock.writeLock().lock();

				List<String> errors = validatorFacade.isValid(rentBook, bookRepositoriesFacade);
				if (!errors.isEmpty()) {
					throw new IllegalArgumentException(getAllErrors(errors));
				}

				bookRepositoriesFacade.addRentBook(rentBook);

				LOG.info("finished rentBook({})", rentBook);
			} finally {
				lock.writeLock().unlock();
			}
		}


	@Override
	public void returnRental(RentBook rentBook) {
		LOG.info("entering returnRental({})", rentBook);

		try{
			lock.writeLock().lock();
			boolean wasReturned = bookRepositoriesFacade.returnRental(rentBook);

			LOG.info("finished returnRental({}, returned {})", rentBook, wasReturned);
		} finally {
			lock.writeLock().unlock();
		}
	}
}
