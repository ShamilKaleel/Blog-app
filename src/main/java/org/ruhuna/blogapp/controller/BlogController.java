package org.ruhuna.blogapp.controller;

import jakarta.validation.Valid;
import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.payload.BlogResponseDTO;
import org.ruhuna.blogapp.payload.CreateBlogDTO;
import org.ruhuna.blogapp.service.impl.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private IBlogService blogService;

    @GetMapping("/all")
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogs() {
        List<BlogResponseDTO> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> getBlogById(@PathVariable Long id) {
        BlogResponseDTO blog = blogService.getBlogById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BlogResponseDTO> createBlog(@RequestBody @Valid CreateBlogDTO createBlogDTO) {
        BlogResponseDTO createdBlog = blogService.createBlog(createBlogDTO);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponseDTO> updateBlog(@PathVariable Long id, @RequestBody @Valid  CreateBlogDTO createBlogDTO) {
        BlogResponseDTO updatedBlog = blogService.updateBlog(id, createBlogDTO);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
