package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table (name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer order_id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(name = "total")
    private Double total;
    @Column(name = "subtotal")
    private Double subtotal;
    @Column(name = "discount_percent")
    private Double discountPercent;
    @Column(name = "date")
    private Date date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id")
    private CouponDiscount coupon;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<OrdersItem> ordersItems;

    public Orders() {
    }



    public Orders(Integer order_id, Customer customer, Double total, Double subtotal, Double discountPercent, Date date, CouponDiscount coupon) {
        this.order_id = order_id;
        this.customer = customer;
        this.total = total;
        this.subtotal = subtotal;
        this.discountPercent = discountPercent;
        this.date = date;
        this.coupon = coupon;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer_id(Customer customer) {
        this.customer = customer;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal(){
        return subtotal ;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public CouponDiscount getCoupon() {
        return coupon;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCoupon(CouponDiscount coupon) {
        this.coupon = coupon;
    }

    public List<OrdersItem> getOrdersItems() {
        return ordersItems;
    }

    public void setOrdersItems(List<OrdersItem> ordersItems) {
        this.ordersItems = ordersItems;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", customer=" + customer +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", coupon=" + coupon +
                ", ordersItems=" + ordersItems +
                '}';
    }
}
