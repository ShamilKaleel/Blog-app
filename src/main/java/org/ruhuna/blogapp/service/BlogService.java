package org.ruhuna.blogapp.service;

import org.modelmapper.ModelMapper;
import org.ruhuna.blogapp.exceptions.ResourceNotFoundException;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.payload.BlogResponseDTO;
import org.ruhuna.blogapp.payload.CommentResponseDTO;
import org.ruhuna.blogapp.repository.BlogRepository;
import org.ruhuna.blogapp.service.impl.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService implements IBlogService {

    @Autowired
    private  BlogRepository blogRepository;

    @Autowired
    private  ModelMapper modelMapper;


    @Override
    public List<Blog> getAllBlogs() {
       List<Blog> blogs= blogRepository.findAll();
       if (blogs.isEmpty()){
           throw new ResourceNotFoundException("No Blogs Found");
         }
        return blogs;
    }

    @Override
    public BlogResponseDTO getBlogById(Long id) {

       Blog blog= blogRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Blog Id: "+ id +" not found"));
         return convertToDTO(blog);
    }

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        blogRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Blog Id: "+ id +" not found"));
        return blogRepository.save(blog);

    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Blog Id: "+ id +" not found"));
        blogRepository.deleteById(id);

    }

    public BlogResponseDTO convertToDTO(Blog blog) {
        BlogResponseDTO dto= modelMapper.map(blog, BlogResponseDTO.class);
        dto.setAuthorName(blog.getUser().getUsername());
        dto.setAuthorEmail(blog.getUser().getEmail());

        dto.setComments(blog.getComments().stream()
                .map(BlogService::mapCommentToDTO)
                .collect(Collectors.toSet()));

        return dto;
    }

    private static CommentResponseDTO mapCommentToDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername()
        );
    }

    public Blog getBlogByIdd(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
    }

}
