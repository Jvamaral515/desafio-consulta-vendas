package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SellerMinDTO {

    private Long id;
    private String name;
    private List<SaleMinDTO> sales = new ArrayList<>();

    public SellerMinDTO(Long id, String name, List<SaleMinDTO> sales) {
        this.id = id;
        this.name = name;
        this.sales = sales;
    }

    public SellerMinDTO(Seller entity) {
        id = entity.getId();
        name = entity.getName();
        sales = entity.getSales().stream().map(x -> new SaleMinDTO(x)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SaleMinDTO> getSales() {
        return sales;
    }
}
