package com.webtodolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtodolist.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskId(int taskId);
    
    List<Comment> findByUserId(int userId);
    
    void deleteByTaskId(int taskId);
}
