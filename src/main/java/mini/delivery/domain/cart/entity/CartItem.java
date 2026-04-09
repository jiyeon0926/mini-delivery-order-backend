package mini.delivery.domain.cart.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.global.common.entity.BaseEntity;

@Entity
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, updatable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false, updatable = false)
    private Menu menu;

    @Column(nullable = false)
    private int quantity = 1;

    private CartItem(Cart cart, Menu menu, int quantity) {
        this.cart = cart;
        this.menu = menu;
        this.quantity = quantity;
    }

    public static CartItem create(Cart cart, Menu menu, int quantity) {
        return new CartItem(cart, menu, quantity);
    }
}
