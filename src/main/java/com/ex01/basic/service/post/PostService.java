package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.dto.post.PostModifyDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.post.MemberNotFoundException;
import com.ex01.basic.exception.post.PostAccessDeniedException;
import com.ex01.basic.exception.post.PostNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemRepository memRepository;
    //post 추가
    public void insert(PostDto postDto, String username){
        MemberEntity memberEntity = memRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("회원 가입 먼저 하세요"));

        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);
        postEntity.setMemberEntity(memberEntity); //외래키 지정

        postRepository.save(postEntity);
    }
    //전체 post 조회
    public List<PostAllDto> getPost(){
        List<PostAllDto> list = postRepository.findAll().stream()
                .map(PostAllDto::new)
                .toList();
        if(list.isEmpty())
            throw new PostNotFoundException("저장된 데이터 없음");
        return list;
    }
    //특정 post 조회
    public PostDetailDto getPostOne(Long id){
        /*
        PostDetailDto postDetailDto = postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new MemberNotFoundException("존재하지 않는 글")
                );
        return postDetailDto;
         */
        return postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(() -> new PostNotFoundException("해당 데이터 없음"));
    }
    //포스트 삭제
    public void postDelete(Long id, String username){
        PostEntity postEntity = postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException("존재하지 않는 글"));
        if(!postEntity.getMemberEntity().getUsername().equals(username))
            throw new PostAccessDeniedException("삭제 권한이 없습니다");
        postRepository.deleteById(id);
    }
    //포스트 수정
    public void update(Long id, PostModifyDto postModifyDto, String username){
        PostEntity postEntity = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("게시글 없음"));
        if(!postEntity.getMemberEntity().getUsername().equals(username))
            throw new PostAccessDeniedException("수정 권한이 없습니다");

        BeanUtils.copyProperties(postModifyDto, postEntity);
        postRepository.save(postEntity);
    }
}
