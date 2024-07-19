package org.gbbv.musikkspring.service;

import jakarta.transaction.Transactional;
import org.gbbv.musikkspring.dto.AddToCartDto;
import org.gbbv.musikkspring.dto.CartDto;
import org.gbbv.musikkspring.model.Album;
import org.gbbv.musikkspring.model.Cart;
import org.gbbv.musikkspring.model.CartCost;
import org.gbbv.musikkspring.repository.AlbumRepository;
import org.gbbv.musikkspring.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final AlbumRepository albumRepository;

    public CartService(CartRepository cartRepository, AlbumService albumService, AlbumRepository albumRepository) {
        this.cartRepository = cartRepository;
        this.albumRepository = albumRepository;
    }

    public List<CartDto> getCartItems(Integer userId) {
        List<Cart> cartItems = cartRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        List<CartDto> cartDtoList = new ArrayList<>();
        for (Cart cart : cartItems) {
            CartDto cartDto = new CartDto(cart);
            cartDtoList.add(cartDto);
        }
        return cartDtoList;
    }

    public void addToCart(AddToCartDto addToCartDto, int userId) {
        Album album = albumRepository.findById(addToCartDto.getAlbumId())
                .orElseThrow(() -> new RuntimeException("Album not found"));
        Cart cart = new Cart(addToCartDto, userId, album);
        cartRepository.save(cart);
    }

    public Cart getAddToCartFromDto(AddToCartDto addToCartDto, int userId, Album album) {
        Cart cart = new Cart(addToCartDto, userId, album);
        return cart;
    }

    public CartCost listCartItems(int userId) {
        List<Cart> cartItems = cartRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        List<CartDto> cartDtoList = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartItems) {
            CartDto cartDto = new CartDto(cart);
            cartDtoList.add(cartDto);
            totalCost += cart.getAlbum().getPrice() * cart.getQuantity();
        }
        return new CartCost(cartDtoList, totalCost);
    }

    public void updateCartItem(int itemID, int quantity, int userId) {
        Cart cart = getCartItemById(itemID);
        if (cart.getUserId().equals(userId)) {
            cart.setQuantity(quantity);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Unauthorized request");
        }
    }

    public void deleteCartItem(int itemID, int userId) {
        Cart cart = cartRepository.findById(itemID).orElseThrow(() -> new RuntimeException("Cart item not found"));
        if (cart.getUserId().equals(userId)) {
            cartRepository.delete(cart);
        } else {
            throw new RuntimeException("Unauthorized request");
        }
    }

    public Cart getCartItemById(int itemID) {
        return cartRepository.findById(itemID).orElseThrow(() -> new RuntimeException("Cart item not found"));
    }

    public void clearCart(Integer id) {
        cartRepository.deleteAllByUserId(id);
    }
}