package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller WHERE obj.date BETWEEN :startDate AND :finalDate AND UPPER(obj.seller.name) LIKE CONCAT('%', UPPER(:name), '%')",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller WHERE obj.date BETWEEN :startDate AND :finalDate AND UPPER(obj.seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
    Page<Sale> searchReport(LocalDate startDate, LocalDate finalDate, String name, Pageable pageable);

    @Query(value = "SELECT new com.devsuperior.dsmeta.dto.SaleSumaryDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj WHERE obj.date BETWEEN :startDate AND :finalDate GROUP BY obj.seller.name")
    List<SaleSumaryDTO> searchSumary(LocalDate startDate, LocalDate finalDate);
}
