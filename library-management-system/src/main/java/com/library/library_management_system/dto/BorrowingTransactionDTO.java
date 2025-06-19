package com.library.library_management_system.dto;

import com.library.library_management_system.emun.TransactionStatus;
import com.library.library_management_system.model.Book;
import com.library.library_management_system.model.Borrower;
import com.library.library_management_system.model.BorrowingTransaction;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowingTransactionDTO {

    private Long id;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;
    private TransactionStatus status;
    private Long bookId;
    private Long borrowerId;

    public static BorrowingTransactionDTO fromEntity(BorrowingTransaction transaction) {
        BorrowingTransactionDTO dto = new BorrowingTransactionDTO();
        dto.setId(transaction.getId());
        dto.setBorrowDate(transaction.getBorrowDate());
        dto.setReturnDate(transaction.getReturnDate());
        dto.setStatus(transaction.getStatus());
        dto.setBookId(transaction.getBook().getId());
        dto.setBorrowerId(transaction.getBorrower().getId());
        return dto;
    }

    public BorrowingTransaction toEntity(Book book, Borrower borrower) {
        BorrowingTransaction tx = new BorrowingTransaction();
        tx.setId(this.id);
        tx.setBorrowDate(this.borrowDate);
        tx.setReturnDate(this.returnDate);
        tx.setStatus(this.status);
        tx.setBook(book);
        tx.setBorrower(borrower);
        return tx;
    }
}
