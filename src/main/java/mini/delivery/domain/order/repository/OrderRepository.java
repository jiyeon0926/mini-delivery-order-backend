package mini.delivery.domain.order.repository;

import mini.delivery.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.store.id = :storeId AND o.store.user.id = :userId")
    Optional<Order> findByIdAndStoreIdAndOwnerId(
            @Param("id") Long orderId,
            @Param("storeId") Long storeId,
            @Param("userId") Long userId
    );

    @Query("SELECT o FROM Order o INNER JOIN FETCH o.store s WHERE o.user.id = :userId ORDER BY o.createdAt DESC")
    List<Order> findAllByUserIdWithStore(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o INNER JOIN FETCH o.store s WHERE o.id = :id AND o.user.id = :userId")
    Optional<Order> findByIdAndUserIdWithStore(@Param("id") Long orderId, @Param("userId") Long userId);
}
