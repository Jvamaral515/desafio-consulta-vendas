package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSumaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportDTO> findById(@PathVariable Long id) {
		SaleReportDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(@RequestParam(name = "minDate", defaultValue = "") String minDate,
														 @RequestParam(name = "maxDate", defaultValue = "") String maxDate,
														 @RequestParam(name = "name", defaultValue = "") String name,
														 Pageable pageable) {
		Page<SaleReportDTO> result = service.searchReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(result);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSumaryDTO>> getSummary(@RequestParam(name = "minDate", defaultValue = "") String minDate,
														  @RequestParam(name = "maxDate", defaultValue = "") String maxDate) {
		List<SaleSumaryDTO> result = service.searchSumary(minDate, maxDate);
		return ResponseEntity.ok(result);
	}
}
