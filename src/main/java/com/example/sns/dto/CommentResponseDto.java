package com.example.sns.dto;

import com.example.sns.entity.Comment;

public class CommentResponseDto {
    private Long id;
    private String content;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
    }

    public Long getId(){
        return id;
    }

    public String getContent(){
        return content;
    }
}
