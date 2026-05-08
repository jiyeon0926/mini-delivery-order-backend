package mini.delivery.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.user.entity.User;
import mini.delivery.global.common.entity.BaseEntity;

@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    @Column(nullable = false)
    private int rating = 1;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isDeleted;

    private Review(User user, Order order, int rating, String content) {
        this.user = user;
        this.order = order;
        this.rating = rating;
        this.content = content;
    }

    public static Review create(User user, Order order, int rating, String content) {
        return new Review(user, order, rating, content);
    }

    public void deleteReview(){
        this.isDeleted=true;
    }
}
