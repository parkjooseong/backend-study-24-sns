package com.example.sns.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sns.repository.LikesRepository;
import com.example.sns.repository.PostRepository;
import com.example.sns.repository.UserRepository;

import com.example.sns.entity.Post;
import com.example.sns.entity.User;
import com.example.sns.entity.Likes;


@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikesController {
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikesController(LikesRepository likesRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public String toggleLike(@PathVariable Long postId, @RequestParam Long userId){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("유효한 게시물이 아닙니다."));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("유효한 유저가 아닙니다."));

        Likes existingLike = likesRepository.findByPostIdAndUserId(postId, userId);

        if(existingLike != null){
            likesRepository.delete(existingLike);
            return "좋아요를 취소했습니다.";
        }else{
            Likes newLike = new Likes();
            newLike.setPost(post);
            newLike.setUser(user);
            likesRepository.save(newLike);
            return "좋아요를 눌렀습니다!";
        }
    }

    @GetMapping("/count")
    public int getLikesCount(@PathVariable Long postId){
        return likesRepository.countByPostId(postId);
    }
}
