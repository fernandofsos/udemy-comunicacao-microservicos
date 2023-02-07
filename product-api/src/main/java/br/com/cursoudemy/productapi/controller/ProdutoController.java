package br.com.cursoudemy.productapi.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
	
	@GetMapping("status")
    public ResponseEntity<HashMap<String, Object>> getStatusProduto() {
		var response = new HashMap<String,Object>();
		
		response.put("Service", "Product-API");
		response.put("Status", "Product-API");
		response.put("httpStatus", HttpStatus.OK.value());
		
		return ResponseEntity.ok(response);
	}
	
}
