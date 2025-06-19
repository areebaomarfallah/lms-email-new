package com.library.library_management_system.dto;

import com.library.library_management_system.model.Borrower;
import lombok.Data;

@Data
public class BorrowerDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public static BorrowerDTO fromEntity(Borrower borrower) {
        BorrowerDTO dto = new BorrowerDTO();
        dto.setId(borrower.getId());
        dto.setName(borrower.getName());
        dto.setEmail(borrower.getEmail());
        dto.setPhoneNumber(borrower.getPhoneNumber());
        return dto;
    }

    public Borrower toEntity() {
        Borrower borrower = new Borrower();
        borrower.setId(this.id);
        borrower.setName(this.name);
        borrower.setEmail(this.email);
        borrower.setPhoneNumber(this.phoneNumber);
        return borrower;
    }
}
