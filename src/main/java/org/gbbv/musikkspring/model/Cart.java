package org.gbbv.musikkspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.gbbv.musikkspring.dto.AddToCartDto;
import org.gbbv.musikkspring.dto.CartDto;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "user_id")
    private @NotBlank Integer userId;

    @Column(name = "album_id")
    private @NotBlank Integer albumId;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "albumId", insertable = false, updatable = false)
    private Album album;


    private int quantity;

    public Cart() {
    }


    public Cart(CartDto cartDto, Album album,int userId){
        this.userId = userId;
        this.albumId = cartDto.getAlbum().getAlbumId();
        this.quantity = cartDto.getQuantity();
        this.album = album;
        this.createdDate = new Date();
    }

    public Cart(@NotBlank Integer userId, @NotBlank Integer albumId, int quantity) {
        this.userId = userId;
        this.albumId = albumId;
        this.createdDate = new Date();
        this.quantity = quantity;
    }

    public Cart(CartDto cartDto, Album album) {
        this.albumId = cartDto.getAlbum().getAlbumId();
        this.quantity = cartDto.getQuantity();
        this.album = album;
        this.createdDate = new Date();
    }

    public Cart(AddToCartDto addToCartDto, int userId, Album album) {
        this.userId = userId;
        this.albumId = addToCartDto.getAlbumId();
        this.quantity = addToCartDto.getQuantity();
        this.album = album;
        this.createdDate = new Date();
    }
}