package com.atp.ecom.productcatalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.productcatalog.model.Product;
import com.atp.ecom.productcatalog.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	TokenService tokenService;

	public Product createProduct(Product product, String token) throws Exception {
		
		if(tokenService.validateToken(token)) {
			return productRepository.save(product);
		}else {
			 throw new Exception("UNAUTHORIZED");
		}
		
	}

	public Optional<Product> getProductById(String id) {
		return productRepository.findById(id);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product updateProduct(String id, Product updatedProduct) {
		return productRepository.findById(id).map(product -> {
			product.setName(updatedProduct.getName());
			product.setDescription(updatedProduct.getDescription());
			product.setPrice(updatedProduct.getPrice());
			product.setQuantity(updatedProduct.getQuantity());
			return productRepository.save(product);
		}).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}

}
