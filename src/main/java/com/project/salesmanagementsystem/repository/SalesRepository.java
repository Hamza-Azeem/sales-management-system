package com.project.salesmanagementsystem.repository;

import com.project.salesmanagementsystem.model.Product;
import com.project.salesmanagementsystem.model.Sales;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByCreationDateBetween(Timestamp start, Timestamp end);

    // ************************************ reports queries **************************************
    @Query(value = "SELECT product_id from product_sales" +
            " WHERE product_sales.sales_id in (SELECT id FROM sales WHERE creation_date BETWEEN :startDate AND :endDate)" +
            "GROUP BY product_id, sales_id ORDER BY COUNT(product_id) DESC LIMIT 3", nativeQuery = true)
    List<Long> findTopProductsBetweenTwoDates(Timestamp startDate, Timestamp endDate);
    @Query(value = "SELECT * FROM sales WHERE creation_date BETWEEN :startDate AND :endDate ORDER BY total DESC", nativeQuery = true)
    List<Sales> findTopPerformingSellers(Timestamp startDate, Timestamp endDate);
    @Query(value = "SELECT client_id FROM sales GROUP BY client_id ORDER BY SUM(total) DESC limit 1", nativeQuery = true)
    Long findTopClient();
}
