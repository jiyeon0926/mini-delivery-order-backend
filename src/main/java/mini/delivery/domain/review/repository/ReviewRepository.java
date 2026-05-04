package mini.delivery.domain.review.repository;

import mini.delivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByOrderId(Long orderId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.isDeleted = false AND r.order.store.id = :storeId")
    double averageRatingByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COALESCE(COUNT(r.id), 0) FROM Review r WHERE r.isDeleted = false AND r.order.store.id = :storeId")
    long countByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r join fetch r.user u where r.order.store.id = :storeId order by r.createdAt desc")
    List<Review> findAllByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r join fetch r.user u join fetch r.order o join fetch o.store s where r.isDeleted = false and u.id = :userId order by r.createdAt desc")
    List<Review> findAllMyReviews(@Param("userId") Long userId);

    @Query("SELECT r.order.id FROM Review r WHERE r.order.id IN :orderIds")
    List<Long> findOrderIdsByOrderIds(@Param("orderIds") List<Long> orderIds);
}
