package com.nadunkawishika.helloshoesapplicationserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    private String supplierId;
    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false,length = 12)
    private String contactNo1;
    @Column(nullable = false,length = 12)
    private String contactNo2;
    @Column(nullable = false,length = 50)
    private String email;
}