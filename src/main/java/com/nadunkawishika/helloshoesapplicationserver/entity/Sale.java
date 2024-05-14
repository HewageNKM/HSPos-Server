package com.nadunkawishika.helloshoesapplicationserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Sale {
    @Id
    @Column(length = 20)
    private String saleId;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false, length = 50)
    private Double unitPrice;

    @Column(nullable = false)
    private Integer itemQTY;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false, length = 50)
    private String paymentMethod;

    @Column(length = 50)
    private Double addedPoints;

    @Column(nullable = false, length = 50)
    private String cashierName;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "sale_item",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> itemList;
}
