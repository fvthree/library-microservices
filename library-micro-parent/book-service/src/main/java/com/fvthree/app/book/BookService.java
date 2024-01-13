package com.fvthree.app.book;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@Transactional
public class BookService {

    private static Logger logger = LogManager.getLogger(BookService.class);

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Map<? , ?> request) {
        String title = request.get("title").toString();

        Book newBook = Book.builder()
                .title(title)
                .author(request.get("author").toString())
                .description(request.get("description").toString())
                .copies(Integer.parseInt(request.get("copies").toString()))
                .copiesAvailable(Integer.parseInt(request.get("copiesAvailable").toString()))
                .category(request.get("category").toString())
                .img(request.get("img").toString())
                .status(BookServiceConstants.ACTIVE_STATUS)
                .build();

        return bookRepository.save(newBook);
    }

    public Book removeBook(Long id) {
        Book bookToRemove = bookRepository.findById(id)
                .orElseThrow(()->new DBException("book with id not found."));
        bookToRemove.setStatus(BookServiceConstants.INACTIVE_STATUS);
        return bookRepository.save(bookToRemove);
    }


    public Book increaseBookQuantity(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new DBException("book with id not found."));
        book.setCopies(book.getCopies()+1);
        book.setCopiesAvailable(book.getCopiesAvailable()+1);
        return bookRepository.save(book);
    }

    public Book decreaseBookQuantity(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(()->new DBException("book with id not found."));
        book.setCopies(book.getCopies()-1);
        book.setCopiesAvailable(book.getCopiesAvailable()-1);
        return bookRepository.save(book);
    }
}
