package com.bookmanagement.bookmanagement.Repository;
import com.bookmanagement.bookmanagement.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
  List<Book> findByBookName(String bookName);

}
