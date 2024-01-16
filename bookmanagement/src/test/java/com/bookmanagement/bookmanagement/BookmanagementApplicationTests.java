package com.bookmanagement.bookmanagement;

import com.bookmanagement.bookmanagement.Repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookmanagementApplicationTests {
	@Autowired
	private BookRepository bookRepository;

	@Test
//	void contextLoads() {
//	}
	public void testAddNew(){
		Book book =new Book();
		book.setBookName("Bluestack");
		book.setAuthor("Vaishnav");
		book.setPrice(99);

//		Book savedBook= bookRepository.save(book);

//		Assertions.assertNotNull(savedBook);
//		assertThat(savedBook.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAll(){
		Iterable<Book> books=bookRepository.findAll();
		assertThat(books).hasSizeGreaterThan(0);

		for(Book book:books){
			System.out.println(book);
		}
	}
	@Test
	public void testUpdate(){
		Integer id=6;
		Optional<Book> optionalBook =bookRepository.findById(id);
		Book book = optionalBook.get();
		book.setBookName("Mahabharat");
		bookRepository.save(book);

		Book updateBook= bookRepository.findById(id).get();
		assertThat(updateBook.getBookName()).isEqualTo("Mahabharat");

	}

	@Test
	public void testGet(){
		Integer id=2;
		Optional<Book> optionalBook=bookRepository.findById(id);
		Book book =optionalBook.get();

		assertThat(optionalBook).isPresent();

	}

	@Test
	public void testDelete(){
		Integer id =6;
		bookRepository.deleteById(id);

		Optional<Book> optionalBook=bookRepository.findById(id);

		assertThat(optionalBook).isNotPresent();
	}

}
