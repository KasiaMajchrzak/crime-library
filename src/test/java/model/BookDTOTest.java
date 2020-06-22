package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import utils.BookTestData;

class BookDTOTest {

	@Test
	void shouldTransferAllDataAndPriceMultipliedByDays() {
		BookDTO dto = new BookDTO(BookTestData.getBook(0), 10);

		assertThat(dto.getId()).isEqualTo(1);
		assertThat(dto.getAuthor()).isEqualTo("Stephen King");
		assertThat(dto.getTitle()).isEqualTo("Mr. Mercedes");
		assertThat(dto.getYear()).isEqualTo(2010);
		assertThat(dto.getPrice()).isEqualTo(20.0);
	}

}