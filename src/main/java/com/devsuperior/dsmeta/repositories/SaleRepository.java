package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT obj FROM Sale obj WHERE obj.date >= :startDate AND obj.date <= :finalDate AND UPPER(obj.seller.name) LIKE CONCAT('%', UPPER(:name), '%')")
    Page<Sale> searchReport(LocalDate startDate, LocalDate finalDate, String name, Pageable pageable);
}
