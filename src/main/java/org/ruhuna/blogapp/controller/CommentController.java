package org.ruhuna.blogapp.controller;

import jakarta.validation.Valid;
import org.ruhuna.blogapp.mapper.CommentMapper;
import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.payload.BlogResponseDTO;
import org.ruhuna.blogapp.payload.CreateCommentDTO;
import org.ruhuna.blogapp.payload.CommentResponseDTO;
import org.ruhuna.blogapp.security.service.UserDetailsServiceImpl;
import org.ruhuna.blogapp.service.BlogService;
import org.ruhuna.blogapp.service.CommentService;
import org.ruhuna.blogapp.service.impl.ICommentService;
import org.ruhuna.blogapp.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BlogService blogService; // Assuming you have a BlogService to retrieve blogs

    // Create a new comment for a blog
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody @Valid CreateCommentDTO createCommentDTO) {
        CommentResponseDTO commentResponseDTO = commentService.createComment(createCommentDTO);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

    // Get a specific comment by ID
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        CommentResponseDTO commentResponseDTO = commentService.getCommentById(id);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    // Update an existing comment
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @RequestBody @Valid CreateCommentDTO createCommentDTO) {
        Comment updatedComment = commentService.updateComment(id, createCommentDTO);
        CommentResponseDTO commentResponseDTO = CommentMapper.toDTO(updatedComment);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    // Delete a comment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all comments for a specific blog
    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByBlogId(@PathVariable Long blogId) {
        List<CommentResponseDTO> comments = commentService.getAllCommentsByBlogId(blogId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
