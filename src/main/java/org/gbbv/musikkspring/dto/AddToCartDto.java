package org.gbbv.musikkspring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gbbv.musikkspring.model.Cart;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private Integer id;
    private @NotNull Integer userId;
    private @NotNull Integer albumId;
    private @NotNull Integer quantity;

    public AddToCartDto(Cart cart) {
        this.setId(cart.getCartId());
        this.setAlbumId(cart.getAlbumId());
        this.setUserId(cart.getUserId());
        this.setQuantity(cart.getQuantity());
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", albumId=" + albumId +
                ", quantity=" + quantity +
                ",";
    }
}