package mini.delivery.domain.menu.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.cart.entity.CartItem;
import mini.delivery.domain.order.entity.OrderItem;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.global.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false, updatable = false)
    private Store store;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "menu")
    private List<CartItem> cartItemList = new ArrayList<>();

    @OneToMany(mappedBy = "menu")
    private List<OrderItem> orderItemList = new ArrayList<>();

    private Menu(Store store, String name, int price) {
        this.store = store;
        this.name = name;
        this.price = price;
    }

    public static Menu create(Store store, String name, int price) {
        return new Menu(store, name, price);
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updatePrice(Integer price){
        this.price = price;
    }

    public void deleteMenu(){
        this.isDeleted=true;
    }
}
