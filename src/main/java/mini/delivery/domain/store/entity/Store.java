package mini.delivery.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.user.entity.User;
import mini.delivery.global.common.entity.BaseEntity;
import mini.delivery.global.common.enums.StoreStatus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 50)
    private String address;

    @Column(nullable = false)
    private int minOrderAmount;

    @Column(nullable = false)
    private LocalTime openTime;

    @Column(nullable = false)
    private LocalTime closeTime;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private StoreStatus storeStatus;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "store")
    private List<Menu> menuList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Order> orderList = new ArrayList<>();

    @Builder
    private Store(User user, String name, String address, int minOrderAmount, LocalTime openTime, LocalTime closeTime, StoreStatus storeStatus) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeStatus = storeStatus;
    }

    public static Store create(User user, String name, String address, int minOrderAmount, LocalTime openTime, LocalTime closeTime) {
        return Store.builder()
                .user(user)
                .name(name)
                .address(address)
                .minOrderAmount(minOrderAmount)
                .openTime(openTime)
                .closeTime(closeTime)
                .storeStatus(StoreStatus.PREPARING)
                .build();
    }
}
