package com.example.sns.controller;
import org.springframework.web.bind.annotation.*;
import com.example.sns.entity.Post;
import com.example.sns.repository.PostRepository;
import java.util.List;
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("잘못된 번호입니다."));
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatePost) {
        Post existingPost = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("잘못된 번호입니다."));
        existingPost.setTitle(updatePost.getTitle());
        existingPost.setContent(updatePost.getContent());

        return postRepository.save(existingPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){
        Post existingPost = postRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("삭제할 게시물이 없습니다"));
        postRepository.delete(existingPost);
    }
}
