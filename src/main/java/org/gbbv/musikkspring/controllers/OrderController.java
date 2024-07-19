package org.gbbv.musikkspring.controllers;

import org.gbbv.musikkspring.exceptions.AlbumNotExistException;
import org.gbbv.musikkspring.mailing.MailSenderService;
import org.gbbv.musikkspring.model.Order;
import org.gbbv.musikkspring.model.User;
import org.gbbv.musikkspring.service.AlbumService;
import org.gbbv.musikkspring.service.OrderService;
import org.gbbv.musikkspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final AlbumService albumService;
    private final MailSenderService mailService;
    public OrderController(OrderService orderService, UserService userService, AlbumService albumService, MailSenderService mailService) {
        this.orderService = orderService;
        this.userService = userService;
        this.albumService = albumService;
        this.mailService = mailService;
    }
    @GetMapping
    public String showUserOrders(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            List<Order> orders = orderService.getOrdersByUserId(user.getId());

            Map<Integer, String> albumNames = new HashMap<>();
            for (Order order : orders) {
                order.getItems().forEach(item -> {
                    String albumName = null;
                    try {
                        albumName = albumService.getAlbumById(item.getAlbumId()).getAlbumName();
                    } catch (AlbumNotExistException e) {
                        throw new RuntimeException(e);
                    }
                    albumNames.put(item.getAlbumId(), albumName);
                });
            }

            model.addAttribute("orders", orders);
            model.addAttribute("albumNames", albumNames);
        } catch (Exception e) {
            System.out.println("Error while processing user orders: " + e.getMessage());
            e.printStackTrace();
        }
        return "userOrders";
    }
}