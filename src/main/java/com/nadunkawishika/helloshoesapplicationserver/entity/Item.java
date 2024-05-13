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
public class Item {
    @Id
    @Column(length = 20)
    private String itemId;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false, length = 5)
    private Double buyingPrice;
    @Column(nullable = false, length = 10)
    private Double sellingPrice;
    @Column(nullable = false, length = 10)
    private Double expectedProfit;
    @Column(nullable = false, length = 10)
    private Double profitMargin;

    @JoinColumn(referencedColumnName = "supplier_name")
    private String supplier_name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_Id")
    private Supplier supplier;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private List<Stock> stockList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "sale_item",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Sale> saleList;
}
