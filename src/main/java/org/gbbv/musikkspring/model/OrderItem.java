package org.gbbv.musikkspring.model;

// New class OrderItem.java
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "album_id")
    private Integer albumId;

    @Column(name = "quantity")
    @Min(0)
    private Integer quantity;

    @Column(name = "price")
    @Min(0)
    private Double price;

}