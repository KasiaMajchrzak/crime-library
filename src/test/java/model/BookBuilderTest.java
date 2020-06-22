package model;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.Test;

class BookBuilderTest {

	private BookBuilder builder = new BookBuilder();

	@Test
	void shouldCreateBook(){
		builder.setId(1).setAuthor("Stephen King").setTitle("Mr. Mercedes").setYear(2010).setPrice(2.0);

		Book book = builder.createBook();

		BookAssert.assertThat(book).hasId(1).hasAuthor("Stephen King").hasTitle("Mr. Mercedes").hasYear(2010).hasPrice(2.0);
	}

	@Test
	void shouldNotCreateBookWhileIdIsNotSet() {
		builder.setAuthor("Stephen King").setTitle("Mr. Mercedes").setYear(2010).setPrice(2.0);

		assertThatIllegalArgumentException().isThrownBy(() -> builder.createBook()).withMessage("Id must be set");
	}

	@Test
	void shouldNotCreateBookWhileAuthorIsNotSet() {
		builder.setId(1).setTitle("Mr. Mercedes").setYear(2010).setPrice(2.0);

		assertThatIllegalArgumentException().isThrownBy(() -> builder.createBook()).withMessage("Required attributes must be set");
	}

	@Test
	void shouldNotCreateBookWhileTitleIsNotSet() {
		builder.setId(1).setAuthor("Stephen King").setYear(2010).setPrice(2.0);

		assertThatIllegalArgumentException().isThrownBy(() -> builder.createBook()).withMessage("Required attributes must be set");
	}

	@Test
	void shouldNotCreateBookWhilePriceIsNotSet() {
		builder.setAuthor("Stephen King").setTitle("Mr. Mercedes").setYear(2010).setId(1);

		assertThatIllegalArgumentException().isThrownBy(() -> builder.createBook()).withMessage("Price must be set to a value greater than 0");
	}
}