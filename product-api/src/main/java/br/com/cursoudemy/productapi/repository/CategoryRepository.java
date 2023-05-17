package br.com.cursoudemy.productapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cursoudemy.productapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{ 
	
	
	//faz a consulta ignorando maiuscula e menuscula e fazendo um like com percente esqueda direita
	List<Category> findByDescriptionIgnoreCaseContaining(String description);

}
