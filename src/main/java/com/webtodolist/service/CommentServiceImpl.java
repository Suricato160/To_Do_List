package com.webtodolist.service;

import com.webtodolist.entity.Comment;
import com.webtodolist.repository.CommentRepository;
<<<<<<< Updated upstream
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

<<<<<<< Updated upstream
=======
    @Autowired
>>>>>>> Stashed changes
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
<<<<<<< Updated upstream
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByTaskId(int taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    @Override
    public List<Comment> findByUserId(int userId) {
        return commentRepository.findByUserId(userId);
=======
    public List<Comment> findByIdTask(int taskId) {
        return commentRepository.findByIdTask(taskId);
    }

    @Override
    public List<Comment> findByIdUser(int userId) {
        return commentRepository.findByIdUser(userId);
>>>>>>> Stashed changes
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
<<<<<<< Updated upstream
    public void deleteById(int id) {
=======
    public void deleteComment(int id) {
>>>>>>> Stashed changes
        commentRepository.deleteById(id);
    }

    @Override
<<<<<<< Updated upstream
    public void deleteByTaskId(int taskId) {
        commentRepository.deleteByTaskId(taskId);
=======
    public void deleteByIdTask(int taskId) {
        commentRepository.deleteByIdTask(taskId);
>>>>>>> Stashed changes
    }

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
<<<<<<< Updated upstream
}
=======

    @Override
    public Optional<Comment> findById(int id) {
        return commentRepository.findById(id);
    }
}
>>>>>>> Stashed changes
