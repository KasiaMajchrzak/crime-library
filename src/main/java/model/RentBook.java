package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class RentBook implements TimeRange{
	private final long bookId;
	private final LocalDateTime from;
	private final LocalDateTime to;

	public RentBook(long bookId, LocalDateTime from, LocalDateTime to) {
		this.bookId = bookId;
		this.from = from;
		this.to = to;
	}

	public long getBookId() {
		return bookId;
	}

	@Override
	public LocalDateTime getFrom() {
		return from;
	}

	@Override
	public LocalDateTime getTo() {
		return to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		RentBook rentBook = (RentBook) o;
		return bookId == rentBook.bookId &&
				Objects.equals(from, rentBook.from) &&
				Objects.equals(to, rentBook.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId, from, to);
	}

	@Override
	public String toString() {
		return "RentBook{" +
				"bookId=" + bookId +
				", from=" + from +
				", to=" + to +
				'}';
	}
}
