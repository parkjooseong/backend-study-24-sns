package com.example.sns.dto;
import jakarta.validation.constraints.NotBlank;

public class PostRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public String getTitle(){
        return title;
    }
    public String getContent() {
        return content;
    }
}
