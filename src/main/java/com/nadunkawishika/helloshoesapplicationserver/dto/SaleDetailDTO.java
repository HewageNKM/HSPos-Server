package com.nadunkawishika.helloshoesapplicationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleDetailDTO {
   private String itemId;
   private String description;
   private String size;
   private Integer quantity;
   private Double price;
   private Double total;
}
