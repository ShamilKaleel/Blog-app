package org.ruhuna.blogapp.service.impl;

import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.payload.BlogResponseDTO;
import org.ruhuna.blogapp.payload.CreateBlogDTO;

import java.util.List;

public interface IBlogService {
    public List<BlogResponseDTO> getAllBlogs();
    public BlogResponseDTO getBlogById(Long id);
    public BlogResponseDTO createBlog(CreateBlogDTO createBlogDTO);
    public BlogResponseDTO updateBlog(Long id, CreateBlogDTO createBlogDTO);
    public void deleteBlog(Long id);
}
