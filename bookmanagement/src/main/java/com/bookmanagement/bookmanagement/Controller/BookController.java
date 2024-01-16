//package com.bookmanagement.bookmanagement.Controller;
//
//import com.bookmanagement.bookmanagement.Book;
//import com.bookmanagement.bookmanagement.Service.BookService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/books")
//public class BookController {
//    Logger logger= LoggerFactory.getLogger(BookController.class);
//
//    @Autowired
//    private BookService bookService;
//
//    @GetMapping
//    public List<Book> getAllBooks() {
//        logger.debug("Listing all the Users");
//        return bookService.getAllBooks();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<Book> getBookById(@PathVariable Long id) {
//        logger.debug("Getting all the users By id");
//        return bookService.getBookById(id);
//    }
//
//
////    @PutMapping("/{id}")
////    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
////        logger.debug("Updating the user by id");
////        return bookService.updateBook(id, updatedBook);
////    }
//
//    @PutMapping("/{id}")
//    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
//        logger.debug("Updating the user by id");
//        return bookService.updateBook(id, updatedBook);
//    }
//
//    @PostMapping
//    public Book saveBook(@RequestBody Book book) {
//        logger.debug("Showing all the Users");
//        return bookService.saveBook(book);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Long id) {
//        logger.debug("Deleting up the users by id");
//        bookService.deleteBook(id);
//    }
//}




package com.bookmanagement.bookmanagement.Controller;

import com.bookmanagement.bookmanagement.BookDTO;
import com.bookmanagement.bookmanagement.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        logger.debug("Listing all the Users");
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<BookDTO> getBookById(@PathVariable Long id) {
        logger.debug("Getting all the users By id");
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        logger.debug("Updating the user by id");
        return bookService.updateBook(id, updatedBookDTO);
    }

    @PostMapping
    public BookDTO saveBook(@RequestBody BookDTO bookDTO) {
        logger.debug("Showing all the Users");
        return bookService.saveBook(bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.debug("Deleting up the users by id");
        bookService.deleteBook(id);
    }
}

