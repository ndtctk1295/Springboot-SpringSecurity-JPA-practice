package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orders_item")
public class OrdersItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer order_item_id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "total")
    private Double total;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id")
    private CouponDiscount coupon;
    @ManyToOne
    @JoinColumn(name = "order_id")
//    @JsonBackReference
    private Orders order;

    public OrdersItem() {
    }

    public OrdersItem(Integer order_item_id, Product product, Integer quantity, Double subtotal, Double total,Orders order_id, CouponDiscount coupon) {
        this.order_item_id = order_item_id;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.total = total;
        this.order = order_id;
        this.coupon = coupon;
    }

    public Integer getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(Integer order_item_id) {
        this.order_item_id = order_item_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setId(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public CouponDiscount getCoupon() {
        return coupon;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCoupon(CouponDiscount coupon) {
        this.coupon = coupon;
    }

    @Override
    public String toString() {
        return "OrdersItem{" +
                "order_item_id=" + order_item_id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", coupon=" + coupon +
                ", order=" + order +
                '}';
    }
}
