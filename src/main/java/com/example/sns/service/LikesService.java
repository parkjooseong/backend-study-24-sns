package com.example.sns.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sns.dto.LikesRequestDto;
import com.example.sns.dto.LikesResponseDto;
import com.example.sns.entity.Likes;
import com.example.sns.entity.Post;
import com.example.sns.entity.User;
import com.example.sns.repository.LikesRepository;
import com.example.sns.repository.PostRepository;
import com.example.sns.repository.UserRepository;


@Service
@Transactional(readOnly = true)
public class LikesService {
    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikesService(LikesRepository likesRepository, PostRepository postRepository, UserRepository userRepository){
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public LikesResponseDto toggleLike(Long postId, LikesRequestDto requestDto){
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("유효한 게시물이 아닙니다."));

        User user = userRepository.findById(requestDto.userId())
            .orElseThrow(() -> new IllegalArgumentException("유효한 사용자가 아닙니다."));

        Optional<Likes> existingLike = likesRepository.findByPostIdAndUserId(postId, requestDto.userId());

        if(existingLike.isPresent()){
            likesRepository.delete(existingLike.get());
            return new LikesResponseDto("좋아요를 취소했습니다.");
        }else{
            Likes newLike = Likes.createLike(user, post);
            likesRepository.save(newLike);
            return new LikesResponseDto("좋아요를 눌렀습니다.");
        }
    }

    public int getLikesCount(Long postId){
        return likesRepository.countByPostId(postId);
    }
}
