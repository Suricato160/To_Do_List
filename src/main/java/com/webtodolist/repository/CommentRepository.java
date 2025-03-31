package com.webtodolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtodolist.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByIdTask(int taskId);
    
    List<Comment> findByIdUser(int userId);
    
    void deleteByIdTask(int taskId);
}
