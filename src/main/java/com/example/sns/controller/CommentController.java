package com.example.sns.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import com.example.sns.entity.Comment;
import com.example.sns.entity.Post;
import com.example.sns.repository.CommentRepository;
import com.example.sns.repository.PostRepository;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @PostMapping
    public Comment createComment(@PathVariable Long postId, @RequestBody Comment comment){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글 입니다."));

        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @GetMapping
    public List<Comment> getCommentsByPost(@PathVariable Long postId){
        return commentRepository.findAllByPostId(postId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment updateComment){
        Comment existingComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 댓글입니다."));

        existingComment.setContent(updateComment.getContent());

        return commentRepository.save(existingComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        Comment existingComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("삭제할 댓글이 없습니다."));

        commentRepository.delete(existingComment);
    }
}
