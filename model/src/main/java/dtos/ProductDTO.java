package dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String uid;

    @JsonProperty("product_name")
    @Size(min = 1, max = 100, message = "Product name is from 1 to 100 characters")
    private String productName;

    @JsonProperty("publisher_name")
    @Size(min = 1, max = 100, message = "Publisher name is from 1 to 100 characters")
    private String publisherName;

    @JsonProperty("price")
    private long price;

    @JsonProperty("description")
    @Size(min = 1, max = 200, message = "Description is from 1 to 200 characters")
    private String description;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("category_uid")
    private String categoryUid;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
