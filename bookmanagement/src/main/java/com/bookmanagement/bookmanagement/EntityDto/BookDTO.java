
package com.bookmanagement.bookmanagement.EntityDto;
import com.bookmanagement.bookmanagement.Entity.Book;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {

    private Integer id;
    private String bookName;
    private String author;
    private Integer price;

    public static BookDTO fromEntity(Book book) {
        return new BookDTO(
                book.getId(),
                book.getBookName(),
                book.getAuthor(),
                book.getPrice()
        );
    }

    public static Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setBookName(bookDTO.getBookName());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        return book;
    }
}

