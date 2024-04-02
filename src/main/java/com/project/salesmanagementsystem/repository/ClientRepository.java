package com.project.salesmanagementsystem.repository;

import com.project.salesmanagementsystem.model.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT COUNT(id), address FROM clients GROUP BY address", nativeQuery = true)
    List<Object[]> findClientAddressStats();
    Optional<Client> findClientByEmail(String email);
    boolean existsByEmail(String email);
}
