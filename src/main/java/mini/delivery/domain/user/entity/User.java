package mini.delivery.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.review.entity.Review;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.global.common.entity.BaseEntity;
import mini.delivery.global.common.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, unique = true, length = 20)
    private String nickname;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Store> storeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    private User(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public static User ofCustomer(String email, String password, String nickname) {
        return new User(email, password, nickname, Role.CUSTOMER);
    }

    public static User ofOwner(String email, String password, String nickname) {
        return new User(email, password, nickname, Role.OWNER);
    }

    public boolean isOwner() {
        return role.equals(Role.OWNER);
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
