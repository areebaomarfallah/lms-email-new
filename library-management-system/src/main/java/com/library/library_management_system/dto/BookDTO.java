package com.library.library_management_system.dto;

import com.library.library_management_system.emun.Category;
import com.library.library_management_system.model.Author;
import com.library.library_management_system.model.Book;
import lombok.Data;

@Data
public class BookDTO {

    private Long id;
    private String title;
    private String isbn;
    private Category category;
    private boolean available;
    private Long authorId;
    private String authorName;

    public static BookDTO fromEntity(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setAvailable(book.isAvailable());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setAuthorName(book.getAuthor().getName());
        return dto;
    }

    public Book toEntity(Author author) {
        Book book = new Book();
        book.setId(this.id);
        book.setTitle(this.title);
        book.setIsbn(this.isbn);
        book.setCategory(this.category);
        book.setAvailable(this.available);
        book.setAuthor(author);
        return book;
    }
}
