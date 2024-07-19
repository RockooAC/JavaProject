package org.gbbv.musikkspring.controllers;

import org.gbbv.musikkspring.dto.AddToCartDto;
import org.gbbv.musikkspring.dto.CartDto;
import org.gbbv.musikkspring.exceptions.AlbumNotExistException;
import org.gbbv.musikkspring.mailing.MailSenderService;
import org.gbbv.musikkspring.model.*;
import org.gbbv.musikkspring.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final AlbumService albumService;
    private final UserService userService;
    private final OrderService orderService;
    private final MailSenderService mailService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService, AlbumService albumService, UserService userService, OrderService orderService, MailSenderService mailService) {
        this.cartService = cartService;
        this.albumService = albumService;
        this.userService = userService;
        this.orderService = orderService;
        this.mailService = mailService;
    }
    @PostMapping("/add")
    public String addToCart(@ModelAttribute AddToCartDto addToCartDto, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        cartService.addToCart(addToCartDto, user.getId());
        model.addAttribute("message", "Added to cart");
        return "redirect:/album/shop";
    }

    @GetMapping
    public String showCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<CartDto> cartItems = cartService.getCartItems(user.getId());
        model.addAttribute("cartItems", cartItems);
        return "cartDetails";
    }

    @PostMapping("/update/{cartItemId}")
    public String updateCartItem(@PathVariable("cartItemId") int itemID, @RequestParam("quantity") int quantity, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        cartService.updateCartItem(itemID, quantity, user.getId());

        model.addAttribute("message", "Cart item updated");
        return "redirect:/cart";
    }
    @PostMapping("/checkoutToOrder")
    public String checkoutToOrder(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        CartCost cartCost = cartService.listCartItems(user.getId());
        model.addAttribute("cartItems", cartCost.getCartItems());
        model.addAttribute("totalCost", cartCost.getTotalCost());

        return "checkout";
    }
    @GetMapping("/orders")
    public String showUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "userOrders";
    }
    @PostMapping("/checkout")
    public String checkout(@ModelAttribute Order order, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        // Get the total cost from the CartCost object
        CartCost cartCost = cartService.listCartItems(user.getId());
        double totalCost = cartCost.getTotalCost();

        // Pass the userId and total cost to the createOrder method
        Order createdOrder = orderService.createOrder(user.getId(), totalCost);
        cartService.clearCart(user.getId());

        try {
            if (auth.getPrincipal() instanceof UserDetails) {
                String email = ((UserDetails) auth.getPrincipal()).getUsername();
                String orderDetails = createdOrder.toString();
                mailService.sendNewMail(email, "Order Created (DET FUNGERER!!)",
                        "Jeg hoper at dere har bestilten noe" +
                                " fint fra vår nettbutikk. Takk for at dere valgte oss. Var det Wardruna? Eller kanskje Aurora? Tash Sultana?" +
                                "\n\nOrder Details:\n" + orderDetails);
            }
        } catch (Exception e) {
            logger.error("Error while sending email: " + e.getMessage());
        }
        model.addAttribute("message", "Order placed successfully");
        return "redirect:/orders";
    }
    @PostMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable("cartItemId") int itemID, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        cartService.deleteCartItem(itemID, user.getId());

        model.addAttribute("message", "Cart item deleted");
        return "redirect:/cart";
    }
}