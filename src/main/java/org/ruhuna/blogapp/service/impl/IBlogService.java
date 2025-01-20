package org.ruhuna.blogapp.service.impl;

import org.ruhuna.blogapp.model.Blog;
import java.util.List;

public interface IBlogService {
    public List<Blog> getAllBlogs();
    public Blog getBlogById(Long id);
    public Blog createBlog(Blog blog);
    public Blog updateBlog(Long id, Blog blog);
    public void deleteBlog(Long id);
}
