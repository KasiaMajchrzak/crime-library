package model;

public class BookDTO {
	private final long id;
	private final String author;
	private final String title;
	private final int year;
	private final double price;

	public BookDTO(Book book, long howManyDays) {
		this.id = book.getId();
		this.author = book.getAuthor();
		this.title = book.getTitle();
		this.year = book.getYear();
		this.price = book.getPrice() * howManyDays;
	}

	public long getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "BookDTO{" +
				"id=" + id +
				", author='" + author + '\'' +
				", title='" + title + '\'' +
				", year=" + year +
				", price=" + price +
				'}';
	}
}
