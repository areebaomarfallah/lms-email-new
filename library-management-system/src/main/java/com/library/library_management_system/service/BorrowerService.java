package com.library.library_management_system.service;

import com.library.library_management_system.client.EmailClient;
import com.library.library_management_system.dto.BorrowerDTO;
import com.library.library_management_system.dto.EmailRequest;
import com.library.library_management_system.model.Borrower;
import com.library.library_management_system.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public List<BorrowerDTO> getAllBorrowers() {
        return borrowerRepository.findAll().stream()
                .map(BorrowerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BorrowerDTO getBorrowerById(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        return BorrowerDTO.fromEntity(borrower);
    }

    public BorrowerDTO createBorrower(BorrowerDTO borrowerDTO) {
        Borrower borrower = borrowerDTO.toEntity();
        borrower = borrowerRepository.save(borrower);
        return BorrowerDTO.fromEntity(borrower);
    }

    public BorrowerDTO updateBorrower(Long id, BorrowerDTO borrowerDTO) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        borrower.setName(borrowerDTO.getName());
        borrower.setEmail(borrowerDTO.getEmail());
        borrower.setPhoneNumber(borrowerDTO.getPhoneNumber());
        borrower = borrowerRepository.save(borrower);
        return BorrowerDTO.fromEntity(borrower);
    }

    public void deleteBorrower(Long id) {
        borrowerRepository.deleteById(id);
    }

    @Autowired
    private EmailClient emailClient; // Inject Feign client

    public void borrowBook(Long borrowerId, String bookTitle) {
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        String email = borrower.getEmail();
        String message = "Book \"" + bookTitle + "\" borrowed successfully";

        EmailRequest emailRequest = new EmailRequest(email, message);
        emailClient.sendEmail(emailRequest);
    }

}
