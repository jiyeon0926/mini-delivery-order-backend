package mini.delivery.global.common.enums;

public enum Role {

    CUSTOMER("고객"),
    OWNER("사장님");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
