package com.bookmanagement.bookmanagement.kafka;

import com.bookmanagement.bookmanagement.EntityDto.BookDTO;
import com.bookmanagement.bookmanagement.Service.BookService;
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

    @KafkaListener(topics = "book-topic", groupId = "book-consumer-group")
    public void consume(String message) {
        try {
            // Convert JSON string to BookDTO
            BookDTO bookDTO = objectMapper.readValue(message, BookDTO.class);

            // Process the received bookDTO
            bookService.saveBook(bookDTO);
            System.out.println("Bhai bhai bhai bhai!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

