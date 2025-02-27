package org.ruhuna.blogapp.mapper;

import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.payload.CommentResponseDTO;

public class CommentMapper {

    public static CommentResponseDTO toDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername()
        );
    }
}
