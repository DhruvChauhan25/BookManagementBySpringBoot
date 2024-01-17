package com.bookmanagement.bookmanagement;

import com.bookmanagement.bookmanagement.Repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookmanagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void testAddNew() throws Exception {
        // Mock the behavior of bookRepository.save
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("Bluestack");
        bookDTO.setAuthor("Vaishnav");
        bookDTO.setPrice(99);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());

    }


    @Test
    public void testListAll() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookName("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(50);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].bookName").value("Test Book"))
                .andExpect(jsonPath("$[0].author").value("Test Author"))
                .andExpect(jsonPath("$[0].price").value(50));
    }

    @Test
    public void testUpdate() throws Exception {
        Integer id = 7;
        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setBookName("Mahabharat");

        when(bookRepository.findById(id)).thenReturn(Optional.of(new Book()));
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        mockMvc.perform(put("/books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDTO)))
                .andExpect(status().isOk());

        verify(bookRepository, times(1)).findById(id);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testGet() throws Exception {
        Integer id = 5;
        Book bookWithId = new Book();
        bookWithId.setId(id);
        // Set other fields as needed

        when(bookRepository.findById(id)).thenReturn(Optional.of(bookWithId));

        mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        verify(bookRepository, times(1)).findById(id);
    }


    @Test
    public void testDelete() throws Exception {
        Integer id = 6;
        doNothing().when(bookRepository).deleteById(id);

        mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isOk());

        verify(bookRepository, times(1)).deleteById(id);
    }
}
