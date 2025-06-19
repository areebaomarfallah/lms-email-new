package com.library.library_management_system.controler;

import com.library.library_management_system.dto.BorrowingTransactionDTO;
import com.library.library_management_system.service.BorrowingTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    public BorrowingTransactionController(BorrowingTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<BorrowingTransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingTransactionDTO> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping("/borrow")
    public ResponseEntity<BorrowingTransactionDTO> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long borrowerId) {
        BorrowingTransactionDTO tx = transactionService.borrowBook(bookId, borrowerId);
        return new ResponseEntity<>(tx, HttpStatus.CREATED);
    }

    @PostMapping("/return/{transactionId}")
    public ResponseEntity<BorrowingTransactionDTO> returnBook(@PathVariable Long transactionId) {
        BorrowingTransactionDTO tx = transactionService.returnBook(transactionId);
        return ResponseEntity.ok(tx);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    // Optional: Get transactions by borrower
    @GetMapping("/borrower/{borrowerId}")
    public ResponseEntity<List<BorrowingTransactionDTO>> getByBorrower(@PathVariable Long borrowerId) {
        return ResponseEntity.ok(transactionService.findByBorrower(borrowerId));
    }

    // Optional: Get transactions by book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BorrowingTransactionDTO>> getByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(transactionService.findByBook(bookId));
    }
}
