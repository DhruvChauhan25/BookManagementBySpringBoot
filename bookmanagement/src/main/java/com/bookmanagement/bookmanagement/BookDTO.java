
package com.bookmanagement.bookmanagement;

public class BookDTO {

    private Integer id;
    private String bookName;
    private String author;
    private Integer price;

    public BookDTO() {
    }

    public BookDTO(Integer id, String bookName, String author, Integer price) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    // Conversion methods

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


