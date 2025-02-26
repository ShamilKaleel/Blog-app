package org.ruhuna.blogapp.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    @NotBlank(message = "Content cannot be blank")
    private String content;

    private Long blogId; // To associate the comment with a specific blog

    private Long userId; // To link the comment to a specific user
}
