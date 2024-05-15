package com.nadunkawishika.helloshoesapplicationserver.dto;

import com.nadunkawishika.helloshoesapplicationserver.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomDTO {
    private String stockId;
    private String supplierId;
    private String supplierName;
    private String itemId;
    private String description;
    private Integer size40;
    private Integer size41;
    private Integer size42;
    private Integer size43;
    private Integer size44;
    private Integer size45;
    private Item item;
}
