package mini.delivery.domain.menu.repository;

import mini.delivery.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByStoreIdAndIsDeletedFalse(Long storeId);

    @Query("SELECT m FROM Menu m INNER JOIN FETCH m.store WHERE m.id = :id AND m.isDeleted = false")
    Optional<Menu> findByIdWithStore(@Param("id") Long menuId);

    @Query("SELECT m FROM Menu m INNER JOIN m.store s WHERE m.id = :id AND s.id = :storeId AND s.user.id = :userId AND m.isDeleted = false")
    Optional<Menu> findByIdAndStoreIdAndUserId(@Param("id") Long menuId, @Param("storeId") Long storeId, @Param("userId") Long userId);
}
