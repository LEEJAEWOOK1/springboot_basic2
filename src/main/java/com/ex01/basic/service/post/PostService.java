package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.dto.post.PostModifyDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostCountEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.post.MemberNotFoundException;
import com.ex01.basic.exception.post.PostAccessDeniedException;
import com.ex01.basic.exception.post.PostNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import com.ex01.basic.repository.post.PostCountRepository;
import com.ex01.basic.repository.post.PostLikeRepository;
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
    private final MemberRepository memberRepository;
    private final PostCountRepository postCountRepository;
    private final PostLikeRepository postLikeRepository;

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
    public List<PostAllDto> getPost(int userId){
        List<PostAllDto> list = postRepository.findAll().stream()
                .map(postEntity -> {
                    Long count = postCountRepository.countByPostEntity_Id(postEntity.getId());
                    boolean liked = false;
                    if(userId != 0)
                        liked = postLikeRepository
                            .existsByMemberEntity_IdAndPostEntity_Id(userId, postEntity.getId());
                    long likedCount = postLikeRepository.countByPostEntity_Id(postEntity.getId());
                    return new PostAllDto(postEntity, count, liked, likedCount);
                })
                //.map(PostAllDto::new)
                .toList();
        if(list.isEmpty())
            throw new PostNotFoundException("저장된 데이터 없음");
        return list;
    }
    //특정 post 조회
    public PostDetailDto getPostOne(Long id, int userId){
        PostDetailDto postDetailDto = postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(() -> new PostNotFoundException("해당 데이터 없음"));
        increasePost(id, userId);
        return postDetailDto;
        /*
        PostDetailDto postDetailDto = postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new MemberNotFoundException("존재하지 않는 글")
                );
        return postDetailDto;
         */
    }
    //조회수(조회기록이 있으면 아무 일 없고, 기록이 없으면 조회 기록 저장), 로그인 회원 기준 조회수 중복 방지
    private void increasePost(Long postId, int userId){
        if(!postCountRepository.existsByMemberEntity_IdAndPostEntity_Id(userId, postId)){
            MemberEntity memberEntity = memRepository.getReferenceById(userId);
            PostEntity post = postRepository.getReferenceById(postId);
            PostCountEntity postCountEntity = new PostCountEntity(memberEntity, post);
            postCountRepository.save(postCountEntity);
        }
    }
    //포스트 삭제
    public void postDelete(Long id, String username){
        PostEntity postEntity = postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException("존재하지 않는 글"));
        if(!postEntity.getMemberEntity().getUsername().equals(username))
            throw new PostAccessDeniedException("삭제 권한이 없습니다");
        postEntity.getPostCounts().clear();
        postEntity.getPostLikes().clear();
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
