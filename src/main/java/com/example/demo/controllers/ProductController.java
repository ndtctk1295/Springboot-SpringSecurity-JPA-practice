package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping(path = "api/v1/products")
public class ProductController {

//    test start
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
//


//    @CrossOrigin(origins = "http://127.0.0.1:5500/") // specify url string to only allow fetching from that url

//    api get all products
    @CrossOrigin()
    @GetMapping("/get-all")

    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query product successfully", productList)
        );
    }

//    api get product by id
    @CrossOrigin()
    @GetMapping("/byId/{id}")
    public ResponseEntity<ResponseObject> findById(@PathVariable Integer id) {
        Optional<Product> foundProduct = productService.findById(id);
        if (foundProduct.isPresent()) {
            Product product = foundProduct.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query product successfully", product)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with id: " + id, null)
            );
        }
    }

//    api get product by name
    @CrossOrigin()
    @GetMapping("/byName/{productName}")
    public ResponseEntity<ResponseObject> findByProductName(@PathVariable String productName){
        List<Product> foundProducts = productService.findByProductName(productName);
        if (!foundProducts.isEmpty()) {
//            Product product = foundProduct.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Query product successfully", foundProducts)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with name: " + productName, null)
            );
        }
    }

//    api GET product by price range
    @CrossOrigin
    @GetMapping("/byPriceRange")
    public ResponseEntity<ResponseObject> getProductsByPriceRange(
            @RequestParam(name = "minPrice", required = true) double minPrice,
            @RequestParam(name = "maxPrice", required = true) double maxPrice) {

        List<Product> foundProducts = productService.getProductsByPriceRange(minPrice, maxPrice);

        if (foundProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find products with price from " + minPrice  + " to " + maxPrice, null));
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query product successfully", foundProducts)
        );
    }

//    api GET products by price ascending order
    @CrossOrigin
    @GetMapping("/get-all-asc")
    public ResponseEntity<ResponseObject> getAllProductsByPriceAsc() {
        List<Product> productList =  productService.getAllProductsByPriceAsc();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query product successfully", productList)
        );
    }

    //    api GET products by price descending order
    @CrossOrigin
    @GetMapping("/get-all-desc")
    public ResponseEntity<ResponseObject> getAllProductsByPriceDesc() {
        List<Product> productList =  productService.getAllProductsByPriceDesc();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Query product successfully", productList)
        );
    }


//      api post new product || postman: raw, json
    @CrossOrigin
    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        List<Product> foundProducts = productService.findByProductName(newProduct.getProductName());

        if (!foundProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Product Name already exists", null)
            );
        }

        Product insertedProduct = productService.insertProduct(newProduct);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Insert Product successfully", insertedProduct)
        );
    }

//    upsert (update or insert if not found)
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Integer id) {
        Product updatedProduct = productService.updateOrInsertProduct(newProduct, id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Updated or Inserted Product successfully", updatedProduct)
        );
    }


//    delete a product || postman: DELETE
    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Integer id) {
        boolean deleted = productService.deleteProduct(id);

        if (deleted) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Delete product successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product to delete", null)
            );
        }
    }

}
