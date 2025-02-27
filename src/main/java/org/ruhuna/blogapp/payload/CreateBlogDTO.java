package org.ruhuna.blogapp.payload;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBlogDTO {


    @NotBlank(message = "Title cannot be blank")
    private String title;

    @Lob
    @NotBlank(message = "Content cannot be blank")
    private String content;

    private Long userID;
}
