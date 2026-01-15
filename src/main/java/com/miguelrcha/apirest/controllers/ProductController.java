package com.miguelrcha.apirest.controllers;

import com.miguelrcha.apirest.dtos.ProductDto;
import com.miguelrcha.apirest.model.Product;
import com.miguelrcha.apirest.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // No momento que compilar o projeto esta classe seja sinalizada como controller
@RequestMapping("/products") // HTTP informa todos os metodos contidos nesta classe
public class ProductController {

    @Autowired // Minimiza todo o trabalho do comentario abaixo
    ProductRepository repository;
    //ProductRepository productRepository = new ProductRepository() {}

    @GetMapping
    public ResponseEntity getAll() {
        List<Product> listProducts = repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Integer id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(product.get());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProductDto dto) { // Momento que for feito a req POST tera que ter um product dentro do body
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
       return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        repository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
    }

    @PutMapping("/{id}")
    public  ResponseEntity update(@PathVariable(value = "id") Integer id, @RequestBody ProductDto dto) {
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product.get();
        BeanUtils.copyProperties(dto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));
    }
}
