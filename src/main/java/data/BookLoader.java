package data;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Arrays;

import model.Book;

public class BookLoader {
	private static final Logger LOG = LoggerFactory.getLogger(BookLoader.class);

	private static ObjectMapper mapper = new ObjectMapper();

	public void load(String fileName, BookRepository bookRepository) {

		LOG.debug("loading books from file {}", fileName);
		try (InputStream fileStream = getClass().getClassLoader().getResourceAsStream(fileName)) {


			Book[] books = mapper.readValue(fileStream, Book[].class);
			bookRepository.update(Arrays.asList(books));
			LOG.debug("books in repository {}", bookRepository.getBooks().size());

		} catch (Exception e) {
			LOG.error("books not loaded", e);
		}
	}
}
