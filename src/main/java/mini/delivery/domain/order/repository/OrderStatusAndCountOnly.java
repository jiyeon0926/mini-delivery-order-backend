package mini.delivery.domain.order.repository;

import mini.delivery.global.common.enums.OrderStatus;

public interface OrderStatusAndCountOnly {

    OrderStatus getOrderStatus();
    Long getCount();
}
