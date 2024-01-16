package com.bookmanagement.bookmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    Logger logger= LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        logger.debug("Showing all up the users");
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        logger.debug("Finding all te user");
        return bookRepository.findById(Math.toIntExact(id));
    }

    public Book saveBook(Book book) {
        logger.debug("Saving the book");
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        logger.debug("Deleting up the users");
        bookRepository.deleteById(Math.toIntExact(id));
    }
}
