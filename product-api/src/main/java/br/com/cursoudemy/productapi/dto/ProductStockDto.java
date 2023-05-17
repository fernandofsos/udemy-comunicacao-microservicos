package br.com.cursoudemy.productapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDto {

	private Integer salesId;
	private List<ProductQuantityDto> products;

}
