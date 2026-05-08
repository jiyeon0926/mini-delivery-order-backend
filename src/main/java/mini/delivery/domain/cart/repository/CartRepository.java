package mini.delivery.domain.cart.repository;

import mini.delivery.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    @Query("SELECT c FROM Cart c INNER JOIN FETCH c.store s WHERE c.user.id = :userId")
    Optional<Cart> findByUserIdWithStore(@Param("userId") Long userId);
}
