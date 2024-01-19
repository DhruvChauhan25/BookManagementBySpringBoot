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
import java.util.Collections;
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

    // Sample JWT token for testing
    private final String jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaHJ1diIsImlhdCI6MTcwNTY0NzI4MywiZXhwIjoxNzA1NjY1MjgzfQ.Qv1L069Clq5zGAyTsxz07GW2MVEybPGi-TlRZLJlinQHZOUiJ-gFxlnQ_2l676Y9MOopGR_GXHLvEqbgA2kPfA";

    @Test
    @WithMockUser
    public void testAddNew() throws Exception {
        when(bookRepository.save(any(Book.class))).thenReturn(new Book());

        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookName("Bluestack");
        bookDTO.setAuthor("Vaishnav");
        bookDTO.setPrice(99);

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testListAll() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setBookName("Test Book");
        book.setAuthor("Test Author");
        book.setPrice(50);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books")
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
    public void testUnauthorizedAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isUnauthorized());
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
    @WithMockUser
    public void testAddNewInvalidInput() throws Exception {
        // Test adding a new book with invalid input (e.g., missing required fields)
        BookDTO invalidBookDTO = new BookDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBookDTO)))
                .andExpect(status().isBadRequest());
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
    @Test
    public void testUpdateNonExistentBook() throws Exception {
        // Test updating details of a book that does not exist
        Integer nonExistentId = 100;
        BookDTO updatedBookDTO = new BookDTO();
        updatedBookDTO.setBookName("Updated Book");

        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/books/{id}", nonExistentId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookDTO)))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser
    public void testDeleteInvalidId() throws Exception {
        // Test deleting a book with an invalid ID
        Integer invalidId = -1;

        mockMvc.perform(delete("/books/{id}", invalidId)
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser
    public void testListAllEmpty() throws Exception {
        // Test listing all books when the repository is empty
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}

