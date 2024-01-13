package com.fvthree.app.book;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fvthree.app.book.BookTestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @Mock
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setup() {
        book = Book.builder()
                .title(BOOK_TITLE)
                .author(BOOK_AUTHOR)
                .category(BOOK_CATEGORY)
                .description(BOOK_DESCRIPTION)
                .copies(0)
                .copiesAvailable(1)
                .status("A")
                .build();
        bookRepository.save(book);
    }

    @DisplayName("JUnit for a book should return by find a title")
    @Test
    public void givenTitle_whenFindByTitle_thenBookObject() {
        String title = book.getTitle();

        when(bookRepository.findByTitle(any(String.class))).thenReturn(Optional.ofNullable(book));

        Book bookDB = bookRepository.findByTitle(title)
                .orElseThrow(() -> new DBException("book not found."));

        Assertions.assertThat(bookDB).isNotNull();
        Assertions.assertThat(bookDB.getTitle()).isEqualTo(BOOK_TITLE);
        Assertions.assertThat(bookDB.getAuthor()).isEqualTo(BOOK_AUTHOR);
        Assertions.assertThat(bookDB.getDescription()).isEqualTo(BOOK_DESCRIPTION);
        Assertions.assertThat(bookDB.getCategory()).isEqualTo(BOOK_CATEGORY);
    }
}
