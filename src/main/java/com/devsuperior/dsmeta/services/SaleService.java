package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate finalDate = convertMaxDate(maxDate);
		LocalDate startDate = convertMinDate(minDate, finalDate);
		Page<Sale> page = repository.searchReport(startDate, finalDate, name, pageable);
        return page.map(x -> new SaleMinDTO(x));
	}

	private LocalDate convertMaxDate(String maxDate) {
		if(maxDate.isEmpty()){
            return LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			return LocalDate.parse(maxDate, fmt);
		}
	}

	private LocalDate convertMinDate(String minDate, LocalDate finalDate) {
		if(minDate.isEmpty()){
            return finalDate.minusYears(1L);
		}
		else{
			return LocalDate.parse(minDate, fmt);
		}
	}
}
