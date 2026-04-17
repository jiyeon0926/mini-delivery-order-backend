package mini.delivery.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewCreateRequestDto {
    @NotNull
    private  final int rating;

    private final String content;
}
