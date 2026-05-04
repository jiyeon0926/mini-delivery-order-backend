package mini.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.cart.entity.CartItem;
import mini.delivery.domain.cart.repository.CartItemRepository;
import mini.delivery.domain.cart.repository.CartRepository;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.order.dto.*;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.entity.OrderItem;
import mini.delivery.domain.order.repository.OrderItemRepository;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.review.repository.ReviewRepository;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public OrderCreateResponseDto createOrder(String address, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUserIdWithStore(user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        Store store = cart.getStore();
        validateStoreOpen(store);

        List<CartItem> cartItems = cartItemRepository.findAllByCartIdWithMenu(cart.getId());
        validateCartNotEmpty(cartItems);

        int totalAmount = calculateCartTotalAmount(cartItems);
        validateMinOrderAmount(store.getMinOrderAmount(), totalAmount);

        Order order = Order.create(user, store, address, totalAmount);
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

    @Transactional
    public void cancelOrder(Long orderId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        validatePendingStatus(order);
        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public List<CustomerOrderResponseDto> getOrders(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Order> orders = orderRepository.findAllByUserIdWithStore(user.getId());

        // orderId 목록 추출 → IN 연산자를 통해 한 번에 조회 → orderId 기준으로 그룹핑
        List<Long> orderIds = extractOrderIds(orders);
        Map<Long, List<OrderItem>> orderItemsMap = getOrderItemsMap(orderIds);

        // IN 연산자를 통해 한 번에 조회
        List<Long> reviewedOrderIds = reviewRepository.findOrderIdsByOrderIds(orderIds);

        return orders.stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemsMap.getOrDefault(order.getId(), List.of());
                    boolean hasReview = reviewedOrderIds.contains(order.getId());

                    return CustomerOrderResponseDto.from(order, hasReview, CustomerOrderItemResponseDto.from(orderItems));
                })
                .toList();
    }

    public CustomerOrderDetailResponseDto getOrderDetail(Long orderId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndUserIdWithStore(orderId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderIdWithMenu(orderId);
        List<OrderItemDetailResponseDto> orderItemDetailResponseDtoList = toOrderItemDetailResponses(orderItems);

        return CustomerOrderDetailResponseDto.from(order, orderItemDetailResponseDtoList);
    }

    private void validateStoreOpen(Store store) {
        if (store.isNotOpenStatus()) {
            throw new CustomException(ErrorCode.STORE_NOT_OPEN);
        }
    }

    private void validateCartNotEmpty(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new CustomException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
    }

    private int calculateCartTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToInt(item -> item.getMenu().getPrice() * item.getQuantity())
                .sum();
    }

    private void validateMinOrderAmount(int minOrderAmount, int cartTotalAmount) {
        if (minOrderAmount > cartTotalAmount) {
            throw new CustomException(ErrorCode.MINIMUM_ORDER_NOT_MET);
        }
    }

    private void clearCartItem(Cart cart) {
        cartItemRepository.deleteAllByCart(cart);
    }

    private void validatePendingStatus(Order order) {
        if (order.isNotPendingStatus()) {
            throw new CustomException(ErrorCode.ORDER_NOT_IN_PENDING_STATUS);
        }
    }

    private List<Long> extractOrderIds(List<Order> orders) {
        return orders.stream()
                .map(Order::getId)
                .toList();
    }

    private Map<Long, List<OrderItem>> getOrderItemsMap(List<Long> orderIds) {
        return orderItemRepository.findAllByOrderIdsWithMenu(orderIds)
                .stream()
                .collect(Collectors.groupingBy(item -> item.getOrder().getId()));
    }

    private List<OrderItemDetailResponseDto> toOrderItemDetailResponses(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    int totalPrice = item.getPrice() * item.getQuantity();

                    return OrderItemDetailResponseDto.from(item, totalPrice);
                })
                .toList();
    }
}
