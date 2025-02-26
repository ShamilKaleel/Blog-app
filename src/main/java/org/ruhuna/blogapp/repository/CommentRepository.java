package org.ruhuna.blogapp.repository;

import org.ruhuna.blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
   List<Comment> findByBlogId(Long blogId);
}
