package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }
    public List<Product> getAllProductsByPriceAsc() {
        return repository.findAllByOrderByPriceAsc();
    }
    public List<Product> getAllProductsByPriceDesc() {
        return repository.findAllByOrderByPriceDesc();
    }


    public Optional<Product> findById (Integer id) {
        return repository.findById(id);
    }
    public List<Product> findByProductName(String productName) {
        return repository.findByProductNameContaining(productName.trim());
    }
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return repository.findByPriceBetween(minPrice, maxPrice);
    }

    public Product insertProduct(Product newProduct) {
        return repository.save(newProduct);
    }
    public Product updateOrInsertProduct(Product newProduct, Integer id) {
        Optional<Product> existingProduct = repository.findById(id);

        return existingProduct.map(product -> {
            product.setProductName(newProduct.getProductName());
            product.setYearProduct(newProduct.getYearProduct());
            product.setPrice(newProduct.getPrice());
            product.setUrl(newProduct.getUrl());
            return repository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return repository.save(newProduct);
        });
    }
    public boolean deleteProduct(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
