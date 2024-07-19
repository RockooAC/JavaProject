package org.gbbv.musikkspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.model.Cart;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Integer id;
    private @NotNull Integer userId;
    private @NotNull Integer quantity;
    private @NotNull Album album;

    public CartDto(Cart cart) {
        this.setId(cart.getCartId());
        this.setUserId(cart.getUserId());
        this.setQuantity(cart.getQuantity());
        this.setAlbum(cart.getAlbum());
    }
    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", quantity=" + quantity +
                ", albumName=" + album.getAlbumName() +
                '}';
    }
    public Integer getAlbumId() {
        return album.getAlbumId();
    }

    public String getAlbumName() {
        return album.getAlbumName();
    }

}