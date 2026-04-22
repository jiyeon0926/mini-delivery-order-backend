package mini.delivery.domain.review.repository;

import mini.delivery.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.isDeleted = false AND r.order.store.id = :storeId")
    double averageRatingByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(r.id) FROM Review r WHERE r.isDeleted = false AND r.order.store.id = :storeId")
    long countByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT r FROM Review r join fetch r.user u where r.isDeleted = false and r.order.store.id = :storeId order by r.createdAt desc")
    List<Review> findAllByStoreId(@Param("storeId") Long storeId);
}
