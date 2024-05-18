package com.nadunkawishika.helloshoesapplicationserver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SaleDTO {
    private String customerId;
    @NotNull(message = "Sale date cannot be empty")
    private List<SaleDetailDTO> saleDetailsList;
    @NotNull(message = "Sale time cannot be empty")
    private Double total;
    @NotNull(message = "Payment description cannot be empty")
    private String paymentDescription;
}
