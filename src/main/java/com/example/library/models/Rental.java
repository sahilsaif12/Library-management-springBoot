package com.example.library.models;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Rental {
    @Id
    private String id;
    
    @NotBlank(message = "BookId is required")
    private String bookId;
    
    @NotBlank(message = "Renter Name is required")
    private String renterName;

    @NotBlank(message = "Rental Date is required")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "Date format must be dd-mm-yyyy")
    private String rentalDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-[0-9]{4}$", message = "Date format must be dd-mm-yyyy")
    private String returnDate;

    private boolean returned;
    

}
