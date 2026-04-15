package mini.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.cart.entity.CartItem;
import mini.delivery.domain.cart.repository.CartItemRepository;
import mini.delivery.domain.cart.repository.CartRepository;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.order.dto.OrderCreateResponseDto;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.entity.OrderItem;
import mini.delivery.domain.order.repository.OrderItemRepository;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public OrderCreateResponseDto createOrder(String address, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUserIdWithStore(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        validateStoreOpen(cart.getStore());

        List<CartItem> cartItems = cartItemRepository.findAllByCartIdWithMenu(cart.getId());
        int totalAmount = calculateTotalAmount(cartItems);

        Order order = Order.create(user, cart.getStore(), address, totalAmount);
        Order savedOrder = orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    Menu menu = item.getMenu();

                    return OrderItem.create(savedOrder, menu, item.getQuantity(), menu.getPrice());
                })
                .toList();
        orderItemRepository.saveAll(orderItems);

        clearCartItem(cart);

        return OrderCreateResponseDto.from(savedOrder);
    }

    private void validateStoreOpen(Store store) {
        if (store.isNotOpenStatus()) {
            throw new CustomException(ErrorCode.STORE_NOT_OPEN);
        }
    }

    private int calculateTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(item -> item.getMenu().getPrice() * item.getQuantity())
                .sum();
    }

    private void clearCartItem(Cart cart) {
        cartItemRepository.deleteAllByCart(cart);
    }
}
