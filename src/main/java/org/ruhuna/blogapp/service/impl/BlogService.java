package org.ruhuna.blogapp.service.impl;

import org.ruhuna.blogapp.exceptions.ResourceNotFoundException;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.repository.BlogRepository;
import org.ruhuna.blogapp.service.IBlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements IBlogService {


    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
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


}
