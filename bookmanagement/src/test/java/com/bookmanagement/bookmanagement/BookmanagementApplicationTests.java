package com.bookmanagement.bookmanagement;

import com.bookmanagement.bookmanagement.Repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookmanagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookRepository bookRepository;
    private final String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaHJ1diIsImlhdCI6MTcwNTkxNzc0OCwiZXhwIjoxNzA1OTE5NTQ4fQ.GLALRe-ku8SWHHuapqgL-Dmt_YKqIbLirh1_k6NmOeY";

    @Test
    @WithMockUser
    public void testAddNew() throws Exception {
        // Mock the behavior of the save method
        when(bookRepository.save(any(Book.class)))
                .thenAnswer(invocation -> {
                    Book savedBook = invocation.getArgument(0);
                    savedBook.setId(1); // Set an example ID for the saved book
                    return savedBook;
                });

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("Bluestack");
        bookDTO.setAuthor("Vaishnav");
        bookDTO.setPrice(99);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()); // Ensure the response includes the ID
    }


    @Test
    public void testListAll() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookName("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(50);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books/new")
                        .header("Authorization", "Bearer " + jwtToken))
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
                        .header("Authorization", "Bearer " + jwtToken)
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
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookWithId));

        mockMvc.perform(get("/books/{id}", id)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 11;
        doNothing().when(bookRepository).deleteById(id);
      mockMvc.perform(delete("/books/{id}", id)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void testBookNotFound() throws Exception {
        Integer nonExistentId = 100;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        mockMvc.perform(get("/books/{id}", nonExistentId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetNonExistentBook() throws Exception {
        // Test getting details of a book that does not exist
        Integer nonExistentId = 100;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/{id}", nonExistentId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

