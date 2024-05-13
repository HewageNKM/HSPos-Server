package com.nadunkawishika.helloshoesapplicationserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Stock {
    @Id
    private Long stockId;
    private String size;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
