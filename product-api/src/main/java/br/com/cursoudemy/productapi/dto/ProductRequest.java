package br.com.cursoudemy.productapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    private Integer supplierId;
    private Integer categoryId;
}