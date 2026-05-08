package mini.delivery.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.user.entity.User;
import mini.delivery.global.common.entity.BaseEntity;
import mini.delivery.global.common.enums.OrderStatus;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, updatable = false)
    private Store store;

    @Column(nullable = false, unique = true, length = 50)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private String orderNumber;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false, length = 20)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(length = 200)
    private String rejectionReason;

    @Column(nullable = false)
    private int totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Builder
    private Order(User user, Store store, String address, OrderStatus orderStatus, String rejectionReason, int totalAmount) {
        this.user = user;
        this.store = store;
        this.address = address;
        this.orderStatus = orderStatus;
        this.rejectionReason = rejectionReason;
        this.totalAmount = totalAmount;
    }

    public static Order create(User user, Store store, String address, int totalAmount) {
        return Order.builder()
                .user(user)
                .store(store)
                .address(address)
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    public boolean isPendingStatus() {
        return orderStatus.equals(OrderStatus.PENDING);
    }

    public boolean isNotPendingStatus() {
        return !isPendingStatus();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void rejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
