package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "products")

public class Product {
//    this is primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment in database
    @Column(name = "product_id")
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_year")
    private int productYear;
    @Column(name = "price")
    private Double price;
    @Column(name = "url")
    private String url;

    public Product() {
    }

    public Product(String productName, int productYear, Double price, String url) {
        this.productName = productName;
        this.productYear = productYear;
        this.price = price;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductYear() {
        return productYear;
    }

    public void setProductYear(int productYear) {
        this.productYear = productYear;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", year=" + productYear +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }
}
