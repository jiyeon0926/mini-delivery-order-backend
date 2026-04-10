package mini.delivery.domain.cart.repository;

import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndMenuId(Cart cart, Long menuId);

    void deleteAllByCart(Cart cart);
}
