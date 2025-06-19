package com.library.library_management_system.service;

import com.library.library_management_system.dto.BorrowingTransactionDTO;
import com.library.library_management_system.emun.TransactionStatus;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Borrower;
import com.library.library_management_system.model.BorrowingTransaction;
import com.library.library_management_system.repository.BookRepository;
import com.library.library_management_system.repository.BorrowerRepository;
import com.library.library_management_system.repository.BorrowingTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowingTransactionService {

    private final BorrowingTransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;

    public BorrowingTransactionService(BorrowingTransactionRepository transactionRepository,
                                       BookRepository bookRepository,
                                       BorrowerRepository borrowerRepository) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.borrowerRepository = borrowerRepository;
    }

    public List<BorrowingTransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(BorrowingTransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BorrowingTransactionDTO getTransactionById(Long id) {
        BorrowingTransaction tx = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return BorrowingTransactionDTO.fromEntity(tx);
    }

    public BorrowingTransactionDTO borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        // Create transaction
        BorrowingTransaction tx = new BorrowingTransaction();
        tx.setBook(book);
        tx.setBorrower(borrower);
        tx.setBorrowDate(LocalDateTime.now());
        tx.setStatus(TransactionStatus.BORROWED);

        // Mark book as unavailable
        book.setAvailable(false);
        bookRepository.save(book);

        tx = transactionRepository.save(tx);
        return BorrowingTransactionDTO.fromEntity(tx);
    }

    public BorrowingTransactionDTO returnBook(Long transactionId) {
        BorrowingTransaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (tx.getStatus() != TransactionStatus.BORROWED) {
            throw new RuntimeException("Book is not currently borrowed");
        }

        tx.setReturnDate(LocalDateTime.now());
        tx.setStatus(TransactionStatus.RETURNED);

        // Mark book available again
        Book book = tx.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        tx = transactionRepository.save(tx);
        return BorrowingTransactionDTO.fromEntity(tx);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Optional: find transactions by borrower or book
    public List<BorrowingTransactionDTO> findByBorrower(Long borrowerId) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        return transactionRepository.findByBorrower(borrower).stream()
                .map(BorrowingTransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<BorrowingTransactionDTO> findByBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return transactionRepository.findByBook(book).stream()
                .map(BorrowingTransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
