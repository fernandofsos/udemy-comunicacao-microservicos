package br.com.cursoudemy.productapi.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.cursoudemy.productapi.dto.SupplierRequest;
import br.com.cursoudemy.productapi.dto.SupplierResponse;
import br.com.cursoudemy.productapi.exception.SuccessResponse;
import br.com.cursoudemy.productapi.exception.ValidationException;
import br.com.cursoudemy.productapi.model.Supplier;
import br.com.cursoudemy.productapi.repository.SupplierRepository;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	private ProductService productService;

	public SupplierService(@Lazy ProductService productService) {
		this.productService = productService;
	}

	public List<SupplierResponse> findAll() {
		return supplierRepository.findAll().stream().map(SupplierResponse::of).collect(Collectors.toList());
	}

	public List<SupplierResponse> findByName(String name) {
		if (isEmpty(name)) {
			throw new ValidationException("The Supplier description must be information");
		}

		return supplierRepository.findByNameIgnoreCaseContaining(name).stream().map(SupplierResponse::of)
				.collect(Collectors.toList());

	}

	public SupplierResponse findByIdResponse(Integer id) {
		return SupplierResponse.of(findById(id));
	}

	public Supplier findById(Integer id) {
		return supplierRepository.findById(id)
				.orElseThrow(() -> new ValidationException("There's no supplier for the given ID. "));
	}

	
	public SupplierResponse save(SupplierRequest request) {
		validaSupplierNomeInformad(request);
		var supplier = supplierRepository.save(Supplier.of(request));
		return SupplierResponse.of(supplier);
	}

	public SupplierResponse update(SupplierRequest request, Integer id) {
		validaSupplierNomeInformad(request);
		validateInformedId(id);
		var supplier = Supplier.of(request);
		supplier.setId(id);
		supplierRepository.save(supplier);
		return SupplierResponse.of(supplier);
	}

	private void validaSupplierNomeInformad(SupplierRequest request) {
		if (isEmpty(request.getName())) {
			throw new ValidationException("The Supplier's name was not informed.");
		}
	}

	public SuccessResponse delete(Integer id) {
		validateInformedId(id);
		if (productService.existsBySupplierId(id)) {
			throw new ValidationException("You cannot delete this supplier because it's already defined by a product.");
		}
		supplierRepository.deleteById(id);
		return SuccessResponse.create("The supplier was deleted.");
	}

	private void validateInformedId(Integer id) {
		if (isEmpty(id)) {
			throw new ValidationException("The supplier ID must be informed.");
		}
	}

}
