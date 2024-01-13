package com.fvthree.app.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.Map;
import java.util.Optional;

import static com.fvthree.app.book.BookTestConstants.BOOK_INACTIVE_STATUS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private static final Logger logger = LogManager.getLogger(BookServiceTest.class);

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setup() {
        book = Book.builder()
                .id(1L)
                .title(BookTestConstants.BOOK_TITLE)
                .author(BookTestConstants.BOOK_AUTHOR)
                .category(BookTestConstants.BOOK_CATEGORY)
                .description(BookTestConstants.BOOK_DESCRIPTION)
                .copies(0)
                .copiesAvailable(0)
                .img("")
                .status("A")
                .build();
    }

    @DisplayName("JUnit when given a map of request, will create a book and return it")
    @Test
    @Order(1)
    public void givenMapRequest_whenCreate_thenReturnABookObject() {
        Map<String, String> request = Map.of(
                "title", "test",
                "category","test",
                "description", "test",
                "author", "test",
                "copies", "0",
                "copiesAvailable", "0",
                "status","A",
                "img", "");

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book createdBook =  bookService.createBook(request);

        Assertions.assertThat(createdBook).isNotNull();
        Assertions.assertThat(createdBook.getTitle()).isEqualTo(book.getTitle());
    }

    @DisplayName("JUnit should update a book to inactive status given by its id")
    @Test
    @Order(2)
    public void givenId_whenRemoveBook_thenUpdateItsStatusToInactive() {
        book.setStatus(BOOK_INACTIVE_STATUS);

        Long id = book.getId();

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book removed = bookService.removeBook(id);

        Assertions.assertThat(removed).isNotNull();
        Assertions.assertThat(removed.getStatus()).isEqualTo(BOOK_INACTIVE_STATUS);
    }

    @DisplayName("JUnit should increment a book copy given by its id")
    @Test
    @Order(3)
    public void givenId_whenIncrementABookCount_thenIncreaseCopiesAndAvailable() {
        int countResult = 1;

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book incrementedBook = bookService.increaseBookQuantity(1L);

        Assertions.assertThat(incrementedBook).isNotNull();
        Assertions.assertThat(incrementedBook.getCopies()).isEqualTo(countResult);
        Assertions.assertThat(incrementedBook.getCopiesAvailable()).isEqualTo(countResult);
    }

    @DisplayName("JUnit should increment a book copy given by its id")
    @Test
    @Order(4)
    public void givenId_whenDecrementABookCount_thenDecreaseCopiesAndAvailable() {
        int totalCount = 0;
        book.setCopies(1);
        book.setCopiesAvailable(1);

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book incrementedBook = bookService.decreaseBookQuantity(1L);

        Assertions.assertThat(incrementedBook).isNotNull();
        Assertions.assertThat(incrementedBook.getCopies()).isEqualTo(totalCount);
        Assertions.assertThat(incrementedBook.getCopiesAvailable()).isEqualTo(totalCount);
    }
}
