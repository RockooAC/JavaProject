package org.gbbv.musikkspring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items;
    public Order() {
    }

    public Order(Integer userId, String orderDate, String status, Double totalPrice) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }
    @Override
    public String toString() {
        return "Order ID: " + orderId + ",  \n" +
                "User ID: " + userId + ",  \n" +
                "Order Date: " + orderDate + ", \n" +
                "Status: " + status + ",  \n" +
                "Total Price: " + totalPrice;
    }
}