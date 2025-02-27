package org.ruhuna.blogapp.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentDTO {

    @NotBlank(message = "Content cannot be blank")
    private String content;
    private Long blogId;
    private Long userId;
}
