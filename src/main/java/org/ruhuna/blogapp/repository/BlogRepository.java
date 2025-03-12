package org.ruhuna.blogapp.repository;

import org.ruhuna.blogapp.model.Blog;
import org.ruhuna.blogapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByCategory(Category category);
}
