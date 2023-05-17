package br.com.cursoudemy.productapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursoudemy.productapi.dto.SupplierRequest;
import br.com.cursoudemy.productapi.dto.SupplierResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.service.SupplierService;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@PostMapping
	public SupplierResponse save(@RequestBody SupplierRequest request) {
		return supplierService.save(request);
	}

	@GetMapping
	public List<SupplierResponse> findAll() {
		return supplierService.findAll();
	}

	@GetMapping("{id}")
	public SupplierResponse findById(@PathVariable Integer id) {
		return supplierService.findByIdResponse(id);
	}

	@GetMapping("name/{name}")
	public List<SupplierResponse> findByDescription(@PathVariable String name) {
		return supplierService.findByName(name);
	}
	
	@DeleteMapping("{id}")
	public SuccessResponse deletar(@PathVariable Integer id) {
		return supplierService.delete(id);
	}
	
	
	@PutMapping("{id}")
	public SupplierResponse update(@RequestBody SupplierRequest request,@PathVariable Integer id) {
		return supplierService.update(request,id);
	}

}
