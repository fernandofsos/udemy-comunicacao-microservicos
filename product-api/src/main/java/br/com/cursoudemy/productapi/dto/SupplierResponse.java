package br.com.cursoudemy.productapi.dto;

import org.springframework.beans.BeanUtils;

import br.com.cursoudemy.productapi.model.Supplier;
import lombok.Data;

@Data
public class SupplierResponse {

	private Integer id;
	private String name;


	public static SupplierResponse of(Supplier supplier) {
		var response = new SupplierResponse();
		BeanUtils.copyProperties(supplier, response);
		return response;
	}
}
