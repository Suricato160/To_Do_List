package com.webtodolist.service;

import com.webtodolist.model.Comment;
import com.webtodolist.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> findCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}