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

import br.com.cursoudemy.productapi.dto.CategoryRequest;
import br.com.cursoudemy.productapi.dto.ProductRequest;
import br.com.cursoudemy.productapi.dto.ProductResponse;
import br.com.cursoudemy.productapi.dto.SupplierResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping
	public ProductResponse save(@RequestBody ProductRequest request) {
		return productService.save(request);
	}

	@GetMapping
	public List<ProductResponse> findAll() {
		return productService.findAll();
	}

	@GetMapping("{id}")
	public ProductResponse findById(@PathVariable Integer id) {
		return productService.findByIdResponse(id);
	}

	@GetMapping("category/{categoryId}")
	public List<ProductResponse> findByCategoryId(@PathVariable Integer id) {
		return productService.findByCategoryId(id);
	}

	@GetMapping("supplier/{supplierId}")
	public List<ProductResponse> findBySupplierId(@PathVariable Integer id) {
		return productService.findBySupplierId(id);
	}

	@GetMapping("name/{name}")
	public List<ProductResponse> findByName(@PathVariable String name) {
		return productService.findByName(name);
	}
	
	@DeleteMapping("{id}")
	public SuccessResponse delete(@PathVariable Integer id) {
		return productService.delete(id);
	}
	
	@PutMapping("{id}")
	public ProductResponse update(@RequestBody ProductRequest request,@PathVariable Integer id) {
		return productService.update(request,id);
	}

}
