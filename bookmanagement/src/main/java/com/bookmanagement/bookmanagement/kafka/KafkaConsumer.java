package com.bookmanagement.bookmanagement.kafka;

import com.bookmanagement.bookmanagement.entityDto.BookDTO;
import com.bookmanagement.bookmanagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final BookService bookService;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(BookService bookService, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "book-queue", groupId = "book-consumer-group")
    public void consume(String message) {
        try {
            BookDTO bookDTO = objectMapper.readValue(message, BookDTO.class);
            bookService.saveBook(bookDTO);
            System.out.println("Book data saved to the database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


