package com.ex01.basic.controller.post;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.service.post.PostLikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {
    private final PostLikeService postLikeService;
    //좋아요 선택
    @PostMapping("/post/{postId}/like")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> likePost(@PathVariable("postId") Long postId,
                                         @AuthenticationPrincipal CustomUserDetails details){
        postLikeService.likePost(postId, details.getId());
        return ResponseEntity.ok().build();
    }
    //좋아요 취소
    @DeleteMapping("/post/{postId}/like")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> unlikePost(@PathVariable("postId") Long postId,
                                         @AuthenticationPrincipal CustomUserDetails details){
        postLikeService.unlikePost(postId, details.getId());
        return ResponseEntity.ok().build();
    }
}
