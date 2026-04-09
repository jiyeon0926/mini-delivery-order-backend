package mini.delivery.global.common.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum Role {

    CUSTOMER("고객"),
    OWNER("사장님");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + name()));
    }
}
