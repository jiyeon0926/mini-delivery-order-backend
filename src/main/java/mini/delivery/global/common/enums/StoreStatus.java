package mini.delivery.global.common.enums;

public enum StoreStatus {

    PREPARING("준비 중"),
    OPEN("영업 중"),
    CLOSE("영업 종료");

    private final String description;

    StoreStatus(String description) {
        this.description = description;
    }

    public static StoreStatus of(String status) throws IllegalArgumentException {
        for (StoreStatus storeStatus : values()) {
            if (storeStatus.name().equals(status)) {
                return storeStatus;
            }
        }

        throw new IllegalArgumentException("해당하는 이름의 상태를 찾을 수 없습니다: " + status);
    }
}
