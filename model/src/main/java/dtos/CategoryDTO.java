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
public class CategoryDTO {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty("name")
    @Size(min = 1, max = 40, message = "Name is from 1 to 40 characters")
    private String name;

    @JsonProperty("description")
    @Size(min = 1, max = 120, message = "Description is from 1 to 120 characters")
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
