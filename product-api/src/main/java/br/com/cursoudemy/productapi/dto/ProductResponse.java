package br.com.cursoudemy.productapi.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cursoudemy.productapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
	
	    private Integer id;
	    private String name;
	    @JsonProperty("quantity_available")
	    private Integer quantityAvailable;
	    @JsonProperty("created_at")
	    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	    private LocalDateTime createdAt;
	    private SupplierResponse supplier;
	    private CategoryResponse category;

	    public static ProductResponse of(Product product) {
	        return ProductResponse
	            .builder()
	            .id(product.getId())
	            .name(product.getName())
	            .quantityAvailable(product.getQuantityAvailable())
	            .createdAt(product.getCreatedAt())
	            .supplier(SupplierResponse.of(product.getSupplier()))
	            .category(CategoryResponse.of(product.getCategory()))
	            .build();
	    }

}
