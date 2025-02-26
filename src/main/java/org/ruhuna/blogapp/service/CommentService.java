//package org.ruhuna.blogapp.service;
//
//import org.ruhuna.blogapp.model.Comment;
//import org.ruhuna.blogapp.model.Blog;
//import org.ruhuna.blogapp.model.User;
//import org.ruhuna.blogapp.payload.CreateCommentDTO;
//import org.ruhuna.blogapp.payload.CommentResponseDTO;
//import org.ruhuna.blogapp.repository.CommentRepository;
//import org.ruhuna.blogapp.repository.BlogRepository; // Assuming you have a Blog repository
//import org.ruhuna.blogapp.repository.UserRepository; // Assuming you have a User repository
//import org.ruhuna.blogapp.mapper.CommentMapper;
//import org.ruhuna.blogapp.service.impl.ICommentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CommentService implements ICommentService {
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private BlogRepository blogRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public CommentResponseDTO createComment(CreateCommentDTO comment) {
//
//        return commentRepository.save(comment);
//
//    }
//
//    @Override
//    public CommentResponseDTO getCommentById(Long id) {
//        Comment comment = commentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//        return CommentMapper.toDTO(comment);
//    }
//
//    @Override
//    public Comment updateComment(Long id, CreateCommentDTO createCommentDTO) {
//        Comment existingComment = commentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Comment not found"));
//
//        existingComment.setContent(createCommentDTO.getContent());
//
//        // Retrieve the Blog and User by ID from DTO
//        Blog blog = blogRepository.findById(createCommentDTO.getBlogId())
//                .orElseThrow(() -> new RuntimeException("Blog not found"));
//        existingComment.setBlog(blog);
//
//        User user = userRepository.findById(createCommentDTO.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        existingComment.setUser(user);
//
//        return commentRepository.save(existingComment);
//    }
//
//    @Override
//    public void deleteComment(Long id) {
//        commentRepository.deleteById(id);
//    }
//
//    @Override
//    public List<CommentResponseDTO> getAllCommentsByBlogId(Long blogId) {
//        List<Comment> comments = commentRepository.findByBlogId(blogId);
//        return comments.stream()
//                .map(CommentMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
////    private CommentResponseDTO mapToDTO(Comment comment) {
////        CommentResponseDTO dto =
////
////    }
//
//}
