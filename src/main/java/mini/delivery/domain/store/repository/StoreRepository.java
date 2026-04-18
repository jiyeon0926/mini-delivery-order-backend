package mini.delivery.domain.store.repository;

import mini.delivery.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, CustomStoreRepository {

    long countByUserId(Long userId);

    Optional<Store> findByIdAndUserId(Long storeId, Long userId);
    
    Optional<Store> findByIdAndUserIdAndIsDeletedFalse(Long storeId, Long userId);

    boolean existsByUserIdAndIsDeletedFalse(Long userId);

    List<Store> findAllByUserId(Long userId);
}
