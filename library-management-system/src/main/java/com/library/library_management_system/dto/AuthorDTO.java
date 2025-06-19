package com.library.library_management_system.dto;

import com.library.library_management_system.model.Author;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AuthorDTO {

    private Long id;
    private String name;
    private String biography;
    private List<Long> bookIds;

    public static AuthorDTO fromEntity(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setBiography(author.getBiography());
        dto.setBookIds(
                author.getBooks() != null ?
                        author.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList())
                        : null
        );
        return dto;
    }

    public Author toEntity() {
        Author author = new Author();
        author.setId(this.id);
        author.setName(this.name);
        author.setBiography(this.biography);
        return author;
    }
}
