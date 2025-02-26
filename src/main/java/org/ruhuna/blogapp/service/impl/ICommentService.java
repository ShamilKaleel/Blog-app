package org.ruhuna.blogapp.service.impl;

import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.payload.CreateCommentDTO;
import org.ruhuna.blogapp.payload.CommentResponseDTO;

import java.util.List;

public interface ICommentService {

    CommentResponseDTO createComment(CreateCommentDTO createCommentDTO);

    CommentResponseDTO getCommentById(Long id);
    Comment updateComment(Long id, CreateCommentDTO createCommentDTO);
    void deleteComment(Long id);
    List<CommentResponseDTO> getAllCommentsByBlogId(Long blogId);
}
