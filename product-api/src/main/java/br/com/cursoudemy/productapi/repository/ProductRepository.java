	package br.com.cursoudemy.productapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursoudemy.productapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByNameIgnoreCaseContaining(String name);

	List<Product> findByCategoryId(Integer id);

	List<Product> findBySupplierId(Integer id);

	Boolean existsByCategoryId(Integer id);

	Boolean existsBySupplierId(Integer id);
}
