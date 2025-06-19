package com.library.library_management_system.controler;

import com.library.library_management_system.dto.BorrowerDTO;
import com.library.library_management_system.service.BorrowerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }
    @GetMapping
    public ResponseEntity<List<BorrowerDTO>> getAllBorrowers() {
        return ResponseEntity.ok(borrowerService.getAllBorrowers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerDTO> getBorrowerById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowerService.getBorrowerById(id));
    }

    @PostMapping
    public ResponseEntity<BorrowerDTO> createBorrower(@RequestBody BorrowerDTO borrowerDTO) {
        BorrowerDTO created = borrowerService.createBorrower(borrowerDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerDTO> updateBorrower(@PathVariable Long id, @RequestBody BorrowerDTO borrowerDTO) {
        BorrowerDTO updated = borrowerService.updateBorrower(id, borrowerDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrower(@PathVariable Long id) {
        borrowerService.deleteBorrower(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/borrowemail")
    public ResponseEntity<String> borrowBook(
            @PathVariable Long id,
            @RequestParam String bookTitle
    ) {
        borrowerService.borrowBook(id, bookTitle);
        return ResponseEntity.ok("Book borrowed and email sent successfully.");
    }
}
