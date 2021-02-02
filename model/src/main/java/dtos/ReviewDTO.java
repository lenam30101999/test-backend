package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDTO {
    private String uid;

    @JsonProperty("rating")
    @Min(0)
    @Max(5)
    private float rating;

    @JsonProperty("comment")
    @Size(min = 1, max = 256, message = "Comment is from 1 to 256 characters")
    private String comment;

    @JsonProperty("user_uid")
    private String userUid;

    @JsonProperty("product_uid")
    private String productUid;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
