package com.project.salesmanagementsystem.controller;

import com.project.salesmanagementsystem.DTO.ClientReportDTO;
import com.project.salesmanagementsystem.DTO.SalesDTO;
import com.project.salesmanagementsystem.DTO.SalesReportDTO;
import com.project.salesmanagementsystem.model.Sales;
import com.project.salesmanagementsystem.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {
    private final SalesService salesService;

    @GetMapping
    public List<SalesDTO> findAllSales(){
        return salesService.findAllSales();
    }
    @PostMapping
    public SalesDTO saveSales(@RequestBody SalesDTO salesDTO){
        return salesService.saveSales(salesDTO);
    }
    @PutMapping
    public SalesDTO updateSales(@RequestBody SalesDTO salesDTO){
        return salesService.updateSales(salesDTO);
    }
    @DeleteMapping("/{id}")
    public void deleteSalesById(@PathVariable long id){
        salesService.deleteSalesById(id);
    }

    // ************************************* Sales reports endpoints **********************************
    @GetMapping("/sales-report")
    public SalesReportDTO getSalesReport(@RequestParam("startDate") Timestamp startDate, @RequestParam("endDate") Timestamp endDate){
        return salesService.getSalesReport(startDate, endDate);
    }
    @GetMapping("/client-report")
    public ClientReportDTO getClientReport(){
        return salesService.getClientReport();
    }
}
