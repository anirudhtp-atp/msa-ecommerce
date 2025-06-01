package com.atp.ecom.productcatalog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atp.ecom.productcatalog.dto.ProductDto;
import com.atp.ecom.productcatalog.dto.StockUpdateEvent;
import com.atp.ecom.productcatalog.model.Product;
import com.atp.ecom.productcatalog.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	StockUpdateEventProducer stockUpdateEventProducer;

	public Product createProduct(ProductDto productDto, String token) throws Exception {
		
		if(tokenService.validateToken(token)) {
			Product product = new Product();
			product.setDescription(productDto.getDescription());
			product.setName(productDto.getName());
			product.setPrice(productDto.getPrice());
			
			product = productRepository.save(product);
			
			StockUpdateEvent data = new StockUpdateEvent();
			data.setProductId(product.getId());
			data.setQuanitity(productDto.getQuantity());
			
			this.stockUpdateEventProducer.publishStockUpdateEvent(data);
			
			return product;
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
			return productRepository.save(product);
		}).orElseThrow(() -> new RuntimeException("Product not found"));
	}

	public void deleteProduct(String id) {
		productRepository.deleteById(id);
	}

}
