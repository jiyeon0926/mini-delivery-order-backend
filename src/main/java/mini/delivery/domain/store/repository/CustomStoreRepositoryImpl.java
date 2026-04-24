package mini.delivery.domain.store.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.menu.entity.QMenu;
import mini.delivery.domain.order.entity.QOrder;
import mini.delivery.domain.review.entity.QReview;
import mini.delivery.domain.store.dto.QStoreSummaryDto;
import mini.delivery.domain.store.dto.StoreSummaryDto;
import mini.delivery.domain.store.entity.QStore;
import mini.delivery.global.common.enums.StoreStatus;

import java.util.List;

@RequiredArgsConstructor
public class CustomStoreRepositoryImpl implements CustomStoreRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoreSummaryDto> searchStores(String keyword) {
        QStore store = QStore.store;
        QReview review = QReview.review;
        QOrder order = QOrder.order;
        QMenu menu = QMenu.menu;

        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(store.isDeleted.eq(false));

        if (keyword != null) {
            conditions.and(store.name.contains(keyword))
                    .or(menu.name.contains(keyword));
        }

        return jpaQueryFactory.select(new QStoreSummaryDto(
                        store.id,
                        store.name,
                        store.storeStatus,
                        store.minOrderAmount,
                        review.rating.avg(),
                        review.id.countDistinct()
                ))
                .from(store)
                .leftJoin(order).on(store.id.eq(order.store.id))
                .leftJoin(review).on(order.id.eq(review.order.id).and(review.isDeleted.eq(false)))
                .leftJoin(menu).on(store.id.eq(menu.store.id))
                .where(conditions)
                .groupBy(store.id)
                .orderBy(new CaseBuilder() // CASE WHEN THEN
                                .when(store.storeStatus.eq(StoreStatus.OPEN))
                                .then(1)
                                .otherwise(0)
                                .desc(),
                        review.rating.avg().desc(),
                        review.id.countDistinct().desc(),
                        store.createdAt.desc())
                .fetch();
    }
}
