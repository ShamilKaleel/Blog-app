package org.ruhuna.blogapp.controller;

import jakarta.validation.Valid;
import org.ruhuna.blogapp.model.Blog;
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
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestBody @Valid Blog blog) {
        Blog createdBlog = blogService.createBlog(blog);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
        Blog updatedBlog = blogService.updateBlog(id, blog);
        return new ResponseEntity<>(updatedBlog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
