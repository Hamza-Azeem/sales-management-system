package com.project.salesmanagementsystem.service;


import com.project.salesmanagementsystem.DTO.ClientReportDTO;
import com.project.salesmanagementsystem.DTO.SalesDTO;
import com.project.salesmanagementsystem.DTO.SalesReportDTO;
import com.project.salesmanagementsystem.model.Sales;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface SalesService {
    public List<SalesDTO> findAllSales();
    public SalesDTO saveSales(SalesDTO salesDTO);
    public SalesDTO updateSales(SalesDTO salesDTO);
    public void deleteSalesById(long id);
    public SalesReportDTO getSalesReport(Timestamp start, Timestamp end);
    public ClientReportDTO getClientReport();

}
