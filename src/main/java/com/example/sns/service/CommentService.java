package com.example.sns.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sns.dto.CommentRequestDto;
import com.example.sns.dto.CommentResponseDto;
import com.example.sns.entity.Comment;
import com.example.sns.entity.Post;
import com.example.sns.repository.CommentRepository;
import com.example.sns.repository.PostRepository;

@Service
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 게시물입니다."));
        
        Comment comment = Comment.createComment(requestDto.content(), post);


        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment);    
    }

    public List<CommentResponseDto> getCommentByPost(Long postId){
        return commentRepository.findAllByPostId(postId).stream()
            .map(CommentResponseDto::new)
            .collect(Collectors.toList());
    }
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto){
        Comment existingComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 댓글입니다."));

        existingComment.updateComment(requestDto.content());
        return new CommentResponseDto(existingComment);
    }
    @Transactional
    public void deleteComment(Long commentId){
        Comment existingComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("삭제할 댓글이 없습니다."));

        commentRepository.delete(existingComment);
    }
}
