package mini.delivery.domain.cart.repository;

import mini.delivery.domain.cart.entity.Cart;
import mini.delivery.domain.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndMenuId(Cart cart, Long menuId);

    void deleteAllByCart(Cart cart);

    @Query("SELECT i FROM CartItem i INNER JOIN i.cart c WHERE i.id = :id AND c.user.id = :userId")
    Optional<CartItem> findByIdAndUserId(@Param("id") Long itemId, @Param("userId") Long userId);
}
