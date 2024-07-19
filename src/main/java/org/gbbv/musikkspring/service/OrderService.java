package org.gbbv.musikkspring.service;

import org.gbbv.musikkspring.dto.CartDto;
import org.gbbv.musikkspring.model.Cart;
import org.gbbv.musikkspring.model.Order;
import org.gbbv.musikkspring.model.OrderItem;
import org.gbbv.musikkspring.model.StatusEnum;
import org.gbbv.musikkspring.repository.CartRepository;
import org.gbbv.musikkspring.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;

    private final CartRepository cartRepository;

    public OrderService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public Order createOrder(int userId, double totalCost) {
        List<CartDto> cartItems = cartService.getCartItems(userId);
        List<OrderItem> orderItems = convertCartItemsToOrderItems(cartItems);
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalPrice(totalCost);
        order.setOrderDate(LocalDateTime.now().toString());
        order.setStatus(StatusEnum.ORDERED.toString());
        return orderRepository.save(order);
    }

    private List<OrderItem> convertCartItemsToOrderItems(List<CartDto> cartItems) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartDto cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setAlbumId(cartItem.getAlbumId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(Double.valueOf(cartItem.getAlbum().getPrice()));
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderRepository.findAllByUserId(userId);
    }
}