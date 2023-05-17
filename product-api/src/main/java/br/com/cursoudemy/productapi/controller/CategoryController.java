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
import br.com.cursoudemy.productapi.dto.CategoryResponse;
import br.com.cursoudemy.productapi.dto.SupplierRequest;
import br.com.cursoudemy.productapi.dto.SupplierResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public CategoryResponse save(@RequestBody CategoryRequest request) {
		return categoryService.save(request);
	}

	@GetMapping
	public List<CategoryResponse> findAll() {
		return categoryService.findByAll();
	}

	@GetMapping("{id}")
	public CategoryResponse findById(@PathVariable Integer id) {
		return categoryService.findByIdResponse(id);
	}

	@GetMapping("description/{description}")
	public List<CategoryResponse> findByDescription(@PathVariable String description) {
		return categoryService.findByDescription(description);
	}
	
	@DeleteMapping("{id}")
	public SuccessResponse delete(@PathVariable Integer id) {
		return categoryService.delete(id);
	}
	
	
	@PutMapping("{id}")
	public CategoryResponse update(@RequestBody CategoryRequest request,@PathVariable Integer id) {
		return categoryService.update(request,id);
	}

}
