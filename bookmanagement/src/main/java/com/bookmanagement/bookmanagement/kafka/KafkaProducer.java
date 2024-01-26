package com.bookmanagement.bookmanagement.kafka;

import com.bookmanagement.bookmanagement.EntityDto.BookDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, BookDTO> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, BookDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBook(BookDTO bookDTO) {
        kafkaTemplate.send("book-topic", bookDTO);
    }
}
