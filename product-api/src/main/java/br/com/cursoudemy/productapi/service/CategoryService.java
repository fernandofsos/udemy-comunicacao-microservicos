package br.com.cursoudemy.productapi.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.productapi.dto.CategoryRequest;
import br.com.cursoudemy.productapi.dto.CategoryResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.exception.ValidationException;
import br.com.cursoudemy.productapi.model.Category;
import br.com.cursoudemy.productapi.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	private ProductService productService;

	public CategoryService(@Lazy ProductService productService) {
		this.productService = productService;
	}
	
	public SuccessResponse delete(Integer id) {
		validateInformedId(id);
		if (productService.existsByCategoryId(id)) {
			throw new ValidationException("You cannot delete this Category because it's already defined by a product.");
		}
		categoryRepository.deleteById(id);
		return SuccessResponse.create("The Category was deleted.");
	}
	
	private void validateInformedId(Integer id) {
		if (isEmpty(id)) {
			throw new ValidationException("The Category ID must be informed.");
		}
	}


	public Category findById(Integer id) {
		if (isEmpty(id)) {
			throw new ValidationException("The category ID must be informad.");
		}
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ValidationException("There's no category for the given ID. "));
	}

	public List<CategoryResponse> findByAll() {
		return categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
	}

	public List<CategoryResponse> findByDescription(String description) {

		if (isEmpty(description)) {
			throw new ValidationException("the category description must be informad.");
		}

		return categoryRepository.findByDescriptionIgnoreCaseContaining(description).stream().map(CategoryResponse::of)
				.collect(Collectors.toList());
	}

	public CategoryResponse save(CategoryRequest request) {
		validaCategoriaNomeInformado(request);
		var category = categoryRepository.save(Category.of(request));
		return CategoryResponse.of(category);
	}

	private void validaCategoriaNomeInformado(CategoryRequest request) {
		if (isEmpty(request.getDescription())) {
			throw new ValidationException("The category description was not informed.");
		}
	}

	public CategoryResponse findByIdResponse(Integer id) {
		return CategoryResponse.of(findById(id));
	}
	
	public CategoryResponse update(CategoryRequest request, Integer id) {
		validaCategoryNomeInformad(request);
		validateInformedId(id);
		var category = Category.of(request);
		category.setId(id);
		categoryRepository.save(category);
		return CategoryResponse.of(category);
	}
	
	private void validaCategoryNomeInformad(CategoryRequest request) {
		if (isEmpty(request.getDescription())) {
			throw new ValidationException("The Category's Description was not informed.");
		}
	}

}
