package com.library.library_management_system.service;

import com.library.library_management_system.dto.AuthorDTO;
import com.library.library_management_system.model.Author;
import com.library.library_management_system.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(AuthorDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorDTO.toEntity();
        author = authorRepository.save(author);
        return AuthorDTO.fromEntity(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.setName(authorDTO.getName());
        author.setBiography(authorDTO.getBiography());
        // books managed separately
        author = authorRepository.save(author);
        return AuthorDTO.fromEntity(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
