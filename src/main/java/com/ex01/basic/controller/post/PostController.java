package com.ex01.basic.controller.post;

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
    //포스트 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<PostAllDto>> getPost(){
        return ResponseEntity.ok(postService.getPost());
    }
    //포스트 특정 목록 조회
    @GetMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostOne(id));
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
