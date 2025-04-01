package com.webtodolist.service;

import com.webtodolist.entity.Comment;
import com.webtodolist.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByTaskId(int taskId) {
        return commentRepository.findByTaskId(taskId); // Allineato al repository
    }

    @Override
    public List<Comment> findByUserId(int userId) {
        return commentRepository.findByUserId(userId); // Allineato al repository
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteByTaskId(int taskId) {
        commentRepository.deleteByTaskId(taskId); // Allineato al repository
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}