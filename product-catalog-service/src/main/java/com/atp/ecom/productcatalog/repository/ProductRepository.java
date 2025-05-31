package com.atp.ecom.productcatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.atp.ecom.productcatalog.model.Product;

public interface ProductRepository extends MongoRepository	<Product, String> {

}
