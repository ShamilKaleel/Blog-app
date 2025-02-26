package org.ruhuna.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName; // Assuming User has a 'name' field
    private String authorEmail;
    private Set<CommentResponseDTO> comments; // Nested DTO for comments
}

