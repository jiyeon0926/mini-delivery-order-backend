package mini.delivery.domain.store.repository;

import mini.delivery.domain.store.dto.StoreSummaryDto;

import java.util.List;

public interface CustomStoreRepository {

    List<StoreSummaryDto> searchStores(String keyword);
}
