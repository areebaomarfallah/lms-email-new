package com.library.library_management_system.service;

import com.library.library_management_system.dto.BookDTO;
import com.library.library_management_system.emun.Category;
import com.library.library_management_system.model.Author;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.repository.AuthorRepository;
import com.library.library_management_system.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookDTO.fromEntity(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        Book book = bookDTO.toEntity(author);
        book.setAvailable(true); // default available
        book = bookRepository.save(book);
        return BookDTO.fromEntity(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setCategory(bookDTO.getCategory());
        book.setAuthor(author);
        book.setAvailable(bookDTO.isAvailable()); // Allow manual toggle
        book = bookRepository.save(book);
        return BookDTO.fromEntity(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search by Title
    public List<BookDTO> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Search by Category
    public List<BookDTO> searchBooksByCategory(Category category) {
        return bookRepository.findByCategory(category).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Search by Author
    public List<BookDTO> searchBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
