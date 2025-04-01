package com.webtodolist.service;

import com.webtodolist.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(int id);
    
    List<Comment> findByTaskId(int taskId);
    
    List<Comment> findByUserId(int userId);
    
    Comment saveComment(Comment comment);
    
    void deleteById(int id);
    
    void deleteByTaskId(int taskId);
    
    List<Comment> findAllComments();
}

