package model;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class BookTest {

	private  ObjectMapper mapper = new ObjectMapper();

	@Test
	void shouldDeserializeFromJson() throws IOException {

		String jsonBook = "{\"id\":1,\"author\":\"Stephen King\",\"title\":\"Mr. Mercedes\",\"year\":2010,\"price\":2.0}";

		Book book = mapper.readValue(jsonBook, Book.class);

		BookAssert.assertThat(book)
				.hasId(1)
				.hasAuthor("Stephen King")
				.hasTitle("Mr. Mercedes")
				.hasYear(2010)
				.hasPrice(2.0f);
	}

}