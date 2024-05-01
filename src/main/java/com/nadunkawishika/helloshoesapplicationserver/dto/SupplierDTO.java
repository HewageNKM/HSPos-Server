package com.nadunkawishika.helloshoesapplicationserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SupplierDTO {
    private String supplierId;

    @NotEmpty(message = "Name Required")
    @Length(min = 3, max = 255, message = "Name too long or too short")
    private String name;

    @NotEmpty(message = "Address Required")
    @Length(min = 5, max = 150)
    private String address;

    @NotEmpty(message = "Contact Number Required")
    @Length(min = 10, max = 12, message = "Not Validate Number")
    private String contactNo1;

    @NotEmpty(message = "Contact Number Required")
    @Length(min = 10, max = 12, message = "Not Validate Number")
    private String contactNo2;

    @NotEmpty(message = "Email Required")
    @Email(message = "Invalid Email")
    private String email;
}
