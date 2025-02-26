package org.ruhuna.blogapp.service;

import org.modelmapper.ModelMapper;
import org.ruhuna.blogapp.exceptions.ResourceNotFoundException;
import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.payload.CreateCommentDTO;
import org.ruhuna.blogapp.payload.CommentResponseDTO;
import org.ruhuna.blogapp.repository.CommentRepository;
import org.ruhuna.blogapp.repository.BlogRepository; // Assuming you have a Blog repository
import org.ruhuna.blogapp.repository.UserRepository; // Assuming you have a User repository
import org.ruhuna.blogapp.service.impl.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentResponseDTO createComment(CreateCommentDTO createCommentDTO) {

        Blog blog = blogRepository.findById(createCommentDTO.getBlogId()).orElseThrow(
                () -> new ResourceNotFoundException("Blog not found")
        );

       User user = userRepository.findById(createCommentDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

       Comment comment = new Comment();
       comment.setContent(createCommentDTO.getContent());
       comment.setBlog(blog);
       comment.setUser(user);
       commentRepository.save(comment);

       return mapToDTO(comment);
    }

    @Override
    public CommentResponseDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found "+ id ));
        return mapToDTO(comment);
    }

    @Override
    public Comment updateComment(Long id, CreateCommentDTO createCommentDTO) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found " + id));

        existingComment.setContent(createCommentDTO.getContent());

        // Retrieve the Blog and User by ID from DTO
        Blog blog = blogRepository.findById(createCommentDTO.getBlogId())
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found " + createCommentDTO.getBlogId()));
        existingComment.setBlog(blog);

        User user = userRepository.findById(createCommentDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found "+ createCommentDTO.getUserId()));
        existingComment.setUser(user);
        return commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found " + id);
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResponseDTO> getAllCommentsByBlogId(Long blogId) {
        // Ensure comments are fetched correctly
        List<Comment> comments = commentRepository.findByBlogId(blogId);

        if ( comments.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for blog with ID: " + blogId);
        }

        // Map the list of Comment objects to CommentResponseDTO using streams
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CommentResponseDTO mapToDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCommenterName(comment.getUser().getUsername());

        return dto;
    }

}
