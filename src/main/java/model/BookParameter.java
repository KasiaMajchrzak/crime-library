package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class BookParameter implements TimeRange{
	private static final int HOURS = 24;
	private String author;
	private String title;
	private int year;
	private double price;
	private LocalDateTime from;
	private LocalDateTime to;

	public String getAuthor() {
		return author;
	}

	public BookParameter setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public BookParameter setTitle(String title) {
		this.title = title;
		return this;
	}

	public int getYear() {
		return year;
	}

	public BookParameter setYear(int year) {
		this.year = year;
		return this;
	}

	public double getPrice() {
		return price;
	}

	public BookParameter setPrice(double price) {
		this.price = price;
		return this;
	}

	@Override
	public LocalDateTime getFrom() {
		return from;
	}

	public BookParameter setFrom(LocalDateTime from){
		this.from = from;
		return this;
	}

	@Override
	public LocalDateTime getTo() {
		return to;
	}

	public BookParameter setTo(LocalDateTime to){
		this.to = to;
		return this;
	}

	public long howManyDays() {
		long hours = Duration.between(from, to).toHours();
		long days = (hours/HOURS);
		return days + (hours - days * HOURS > 0 ? 1 : 0);
	}

	@Override
	public String toString() {
		return "BookParameter{" +
				"author='" + author + '\'' +
				", title='" + title + '\'' +
				", year=" + year +
				", price=" + price +
				", from=" + from +
				", to=" + to +
				'}';
	}
}
