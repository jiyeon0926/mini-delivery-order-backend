package mini.delivery.domain.order.repository;

import mini.delivery.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT i FROM OrderItem i INNER JOIN FETCH i.menu m WHERE i.order.id = :orderId")
    List<OrderItem> findAllByOrderIdWithMenu(@Param("orderId") Long orderId);

    @Query("SELECT i From OrderItem i INNER JOIN FETCH i.menu m WHERE i.order.id IN :orderIds")
    List<OrderItem> findAllByOrderIdsWithMenu(@Param("orderIds") List<Long> orderIds);
}
