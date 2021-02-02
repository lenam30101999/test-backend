package dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataResponseDTO {
  public Object data;
  public Object message;
  public Object statusCode;
  public Object description;
}
