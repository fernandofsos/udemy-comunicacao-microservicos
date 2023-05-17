package br.com.cursoudemy.productapi.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.productapi.dto.ProductRequest;
import br.com.cursoudemy.productapi.dto.ProductResponse;
import br.com.cursoudemy.productapi.dto.ProductStockDto;
import br.com.cursoudemy.productapi.dto.SupplierRequest;
import br.com.cursoudemy.productapi.dto.SupplierResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.exception.ValidationException;
import br.com.cursoudemy.productapi.model.Product;
import br.com.cursoudemy.productapi.model.Supplier;
import br.com.cursoudemy.productapi.repository.ProductRepository;

@Service
public class ProductService {

	private static final Integer ZERO = 0;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SupplierService supplierService;

	public ProductResponse save(ProductRequest request) {
		validateProductDataInformed(request);
		validateCategoryAndSupplierIdInformed(request);
		var category = categoryService.findById(request.getCategoryId());
		var supplier = supplierService.findById(request.getCategoryId());
		var product = productRepository.save(Product.of(request, supplier, category));
		return ProductResponse.of(product);
	}

	private void validateProductDataInformed(ProductRequest request) {
		if (isEmpty(request.getName())) {
			throw new ValidationException("The product's name was not informed.");
		}
		if (isEmpty(request.getQuantityAvailable())) {
			throw new ValidationException("The product's quantity was not informed.");
		}
		if (request.getQuantityAvailable() <= ZERO) {
			throw new ValidationException("The quantity should not be less or equal to zero.");
		}
	}

	private void validateCategoryAndSupplierIdInformed(ProductRequest request) {
		if (isEmpty(request.getCategoryId())) {
			throw new ValidationException("The category ID was not informed.");
		}
		if (isEmpty(request.getSupplierId())) {
			throw new ValidationException("The supplier ID was not informed.");
		}
	}

	public List<ProductResponse> findAll() {
		return productRepository.findAll().stream().map(ProductResponse::of).collect(Collectors.toList());
	}

	public List<ProductResponse> findByName(String name) {
		if (isEmpty(name)) {
			throw new ValidationException("The product name must be informed.");
		}
		return productRepository.findByNameIgnoreCaseContaining(name).stream().map(ProductResponse::of)
				.collect(Collectors.toList());
	}

	public List<ProductResponse> findBySupplierId(Integer supplierId) {
		if (isEmpty(supplierId)) {
			throw new ValidationException("The product' supplier ID name must be informed.");
		}
		return productRepository
				.findBySupplierId(supplierId)
				.stream()
				.map(ProductResponse::of)
				.collect(Collectors.toList());
	}

	public List<ProductResponse> findByCategoryId(Integer categoryId) {
		if (isEmpty(categoryId)) {
			throw new ValidationException("The product' category ID name must be informed.");
		}
		return productRepository
				.findByCategoryId(categoryId)
				.stream()
				.map(ProductResponse::of)
				.collect(Collectors.toList());
	}

	public ProductResponse findByIdResponse(Integer id) {
		return ProductResponse.of(findById(id));
	}

	public Product findById(Integer id) {
		validateInformedId(id);
		return productRepository.findById(id)
				.orElseThrow(() -> new ValidationException("There's no product for the given ID."));
	}

	public Boolean existsByCategoryId(Integer categoryId) {
		return productRepository.existsByCategoryId(categoryId);
	}

	public Boolean existsBySupplierId(Integer supplierId) {
		return productRepository.existsBySupplierId(supplierId);
	}

	public SuccessResponse delete(Integer id) {
		validateInformedId(id);
	    productRepository.deleteById(id);
		return SuccessResponse.create("The product was deleted.");
	}
	
	private void validateInformedId(Integer id) {
		if (isEmpty(id)) {
			throw new ValidationException("The Product ID must be informed.");
		}
	}
	
	public ProductResponse update(ProductRequest request, Integer id) {
		validaProductNomeInformad(request);
		validateInformedId(id);
	    var category = categoryService.findById(request.getCategoryId());
	    var supplier = supplierService.findById(request.getSupplierId());
		var product = Product.of(request, supplier, category);
		product.setId(id);
		productRepository.save(product);
		return ProductResponse.of(product);
	}
	
	private void validaProductNomeInformad(ProductRequest request) {
		if (isEmpty(request.getName())) {
			throw new ValidationException("The Product's name was not informed.");
		}
	}
	
	public void updateProductStock(ProductStockDto product) {
		
	}


}
