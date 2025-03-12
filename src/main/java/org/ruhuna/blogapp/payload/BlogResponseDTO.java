package org.ruhuna.blogapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ruhuna.blogapp.model.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String authorEmail;
    private Category category;
}

