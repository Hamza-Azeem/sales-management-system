package com.project.salesmanagementsystem.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class SalesReportDTO {
    private int totalSales;
    private double totalRevenue;
    private List<ProductDTO> topSellingProducts;
    private List<SalesDTO> topPerformingSellers;

}
