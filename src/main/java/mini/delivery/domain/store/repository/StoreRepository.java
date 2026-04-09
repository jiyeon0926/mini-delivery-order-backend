package mini.delivery.domain.store.repository;

import mini.delivery.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    long countByUserId(Long userId);

    Optional<Store> findByIdAndUserId(Long storeId, Long userId);
}
