package br.com.cursoudemy.productapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {
	
	private Integer productId;
	
	private Integer quatity;

}
