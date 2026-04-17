package mini.delivery.global.common.enums;

public enum OrderStatus {

    PENDING("주문 확인"),
    COOKING("조리 중"),
    DELIVERING("배달 중"),
    DELIVERED("배달 완료");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public static OrderStatus of(String status) throws IllegalArgumentException {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.name().equals(status)) {
                return orderStatus;
            }
        }

        throw new IllegalArgumentException("해당하는 이름의 상태를 찾을 수 없습니다: " + status);
    }

    public boolean canChangeTo(OrderStatus targetStatus) {
        return switch (this) {
            case PENDING -> targetStatus == COOKING;
            case COOKING -> targetStatus == DELIVERING;
            case DELIVERING -> targetStatus == DELIVERED;
            case DELIVERED -> false;
        };
    }
}
