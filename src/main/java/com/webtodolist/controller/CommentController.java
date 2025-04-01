package com.webtodolist.controller;

import com.webtodolist.entity.Comment;
import com.webtodolist.service.CommentService;
<<<<<<< HEAD
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
=======

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
>>>>>>> abccd9115538223eb2da639e009fff9e1e76df67
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public String getCommentById(@PathVariable int id, Model model) {
        commentService.findById(id).ifPresent(comment -> model.addAttribute("comment", comment));
        return "comment/detail";
    }

    @GetMapping("/task/{taskId}")
    public String getCommentsByTaskId(@PathVariable int taskId, Model model) {
        model.addAttribute("comments", commentService.findByTaskId(taskId));
        model.addAttribute("taskId", taskId);
        return "comment/list";
    }

    @GetMapping("/user/{userId}")
    public String getCommentsByUserId(@PathVariable int userId, Model model) {
        model.addAttribute("comments", commentService.findByUserId(userId));
        model.addAttribute("userId", userId);
        return "comment/user-comments";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("comment", new Comment());
        return "comment/form";
    }

    @PostMapping
    public String createComment(@ModelAttribute Comment comment) {
        commentService.saveComment(comment);
        return "redirect:/comments/task/" + comment.getId();
    }

    @GetMapping("/{id}/delete")
    public String deleteComment(@PathVariable int id) {
        Comment comment = commentService.findById(id).orElse(null);
        int taskId = 0;
        if (comment != null) {
            taskId = comment.getId();
            commentService.deleteById(id);
        }
        return "redirect:/comments/task/" + taskId;
    }

    @GetMapping("/task/{taskId}/delete")
    public String deleteCommentsByTaskId(@PathVariable int taskId) {
        commentService.deleteByTaskId(taskId);
        return "redirect:/tasks/" + taskId;
    }

    @GetMapping
    public String getAllComments(Model model) {
        model.addAttribute("comments", commentService.findAllComments());
        return "comment/all";
    }
}