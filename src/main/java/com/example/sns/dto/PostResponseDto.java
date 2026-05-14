package com.example.sns.dto;

import com.example.sns.entity.Post;

public class PostResponseDto {
    private Long id;
    private String title;
    private String content;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
}
