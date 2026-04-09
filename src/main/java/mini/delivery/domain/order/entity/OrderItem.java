package mini.delivery.domain.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.global.common.entity.BaseEntity;

@Entity
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    private Menu menu;

    @Column(nullable = false)
    private int quantity = 1;

    @Column(nullable = false)
    private int price;

    private OrderItem(Order order, Menu menu, int quantity, int price) {
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderItem create(Order order, Menu menu, int quantity, int price) {
        return new OrderItem(order, menu, quantity, price);
    }
}
