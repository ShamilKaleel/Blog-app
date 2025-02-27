package org.ruhuna.blogapp.service;

import org.modelmapper.ModelMapper;
import org.ruhuna.blogapp.exceptions.ResourceNotFoundException;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.model.Comment;
import org.ruhuna.blogapp.model.User;
import org.ruhuna.blogapp.payload.BlogResponseDTO;
import org.ruhuna.blogapp.payload.CommentResponseDTO;
import org.ruhuna.blogapp.payload.CreateBlogDTO;
import org.ruhuna.blogapp.repository.BlogRepository;
import org.ruhuna.blogapp.repository.UserRepository;
import org.ruhuna.blogapp.security.service.UserDetailsServiceImpl;
import org.ruhuna.blogapp.service.impl.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogService implements IBlogService {

    @Autowired
    private  BlogRepository blogRepository;

    @Autowired
    private  ModelMapper modelMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BlogResponseDTO> getAllBlogs() {
       List<Blog> blogs= blogRepository.findAll();
       if (blogs.isEmpty()){
           throw new ResourceNotFoundException("No Blogs Found");
         }
        return blogs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BlogResponseDTO getBlogById(Long id) {

       Blog blog= blogRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Blog Id: "+ id +" not found"));
         return convertToDTO(blog);
    }

    @Override
    public BlogResponseDTO createBlog(CreateBlogDTO createBlogDTO) {
        Optional<User> userOpt= userRepository.findById(createBlogDTO.getUserID());
        User user = userOpt.orElseThrow(() ->
                new UsernameNotFoundException("User not found" ));
        Blog blog= modelMapper.map(createBlogDTO, Blog.class);
        blog.setUser(user);
        blogRepository.save(blog);

        return convertToDTO(blog);
    }

    @Override
    public BlogResponseDTO updateBlog(Long id, CreateBlogDTO createBlogDTO) {
        Blog blog = blogRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("Blog Id: "+ id +" not found"));
        blog.setTitle(createBlogDTO.getTitle());
        blog.setContent(createBlogDTO.getContent());
        blogRepository.save(blog);
        return convertToDTO(blog);

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

        return dto;
    }



}
