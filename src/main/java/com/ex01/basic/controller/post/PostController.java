package com.ex01.basic.controller.post;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.dto.post.PostModifyDto;
import com.ex01.basic.service.post.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    //포스트 추가
    @PostMapping
    @SecurityRequirement(name="JWT")
    public ResponseEntity<String> insert(@ParameterObject @ModelAttribute PostDto postDto, Authentication authentication){
        postService.insert(postDto, authentication.getName());
        return ResponseEntity.ok("데이터 추가 성공");
    }
    //포스트 전체 목록 조회(permitall로도 되고 토큰 인증도 됨)
    @GetMapping
    @SecurityRequirement(name="JWT")
    public ResponseEntity<List<PostAllDto>> getPost(
            @AuthenticationPrincipal CustomUserDetails userDetails){
        //System.out.println("userDetails : "+userDetails);//그냥 조회 시:null, 토큰 인증 시: 사용자 정보(객체형태로)
        int userId = 0;
        if(userDetails != null)
            userId = userDetails.getId();
        return ResponseEntity.ok(postService.getPost(userId));
    }
    //포스트 특정 목록 조회
    @GetMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable("id") Long id, Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        System.out.println("user details : "+userDetails);
        return ResponseEntity.ok(postService.getPostOne(id, userDetails.getId()));
    }
    //포스트 삭제
    @DeleteMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> postDelete(
            @PathVariable("id") Long id, Authentication authentication){
        postService.postDelete(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
    //포스트 수정
    @PutMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @ParameterObject @ModelAttribute PostModifyDto postModifyDto,
                                       Authentication authentication){
        postService.update(id, postModifyDto, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
