package com.example.sns.dto;

public class LikesResponseDto {
    private String message;

    public LikesResponseDto(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
