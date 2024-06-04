package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public SaleReportDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDTO(entity);
	}

	public Page<SaleReportDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate finalDate = maxDate.isEmpty() ? today : LocalDate.parse(maxDate, fmt);
		LocalDate startDate = minDate.isEmpty() ? finalDate.minusYears(1L) : LocalDate.parse(minDate, fmt);
		Page<Sale> page = repository.searchReport(startDate, finalDate, name, pageable);
        return page.map(x -> new SaleReportDTO(x));
	}

	public List<SaleSumaryDTO> searchSumary(String minDate, String maxDate){
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate finalDate = maxDate.isEmpty() ? today : LocalDate.parse(maxDate, fmt);
		LocalDate startDate = minDate.isEmpty() ? finalDate.minusYears(1L) : LocalDate.parse(minDate, fmt);
		List<SaleSumaryDTO> list = repository.searchSumary(startDate, finalDate);
		return list;
	}

}
