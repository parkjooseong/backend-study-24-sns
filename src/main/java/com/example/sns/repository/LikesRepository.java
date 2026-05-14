package com.example.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sns.entity.Likes;
public interface LikesRepository extends JpaRepository<Likes, Long>{
    Likes findByPostIdAndUserId(Long postId, Long userId);

    int countByPostId(Long postId);
}
