package com.miguelrcha.apirest.repositories;

import com.miguelrcha.apirest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<T, ID>
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
