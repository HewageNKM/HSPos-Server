package com.nadunkawishika.helloshoesapplicationserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    @Id
    @Column(length = 20)
    private String employeeId;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private CivilStatus status;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String image;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 100)
    private String designation;

    @Column(nullable = false, length = 30)
    private String attachBranch;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "role")
    private Role role;

    private LocalDate dob;
    private LocalDate doj;

    @Column(nullable = false, length = 12, unique = true)
    private String contact;

    @Column(nullable = false, length = 50, unique = true)
    @JoinColumn(name = "email")
    private String email;

    @Column(nullable = false, length = 30)
    private String lane;

    @Column(nullable = false, length = 30)
    private String city;

    @Column(nullable = false, length = 30)
    private String state;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String guardianName;

    @Column(nullable = false, length = 12)
    private String guardingContact;

    @OneToOne(mappedBy = "employee")
    private User user;
}
