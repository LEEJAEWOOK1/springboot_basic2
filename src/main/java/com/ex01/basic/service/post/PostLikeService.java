package com.ex01.basic.service.post;

import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.entity.post.PostLikeEntity;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.post.PostLikeRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final MemRepository memRepository;
    private final PostRepository postRepository;
    //좋아요 선택(데이터가 존재하면 좋아요 선택됨, 존재하지 않으면 해당 회원의 해당 게시글에 대한 데이터 저장(좋아요 선택))
    public void likePost(Long postId, int userId){
        if(postLikeRepository.existsByMemberEntity_IdAndPostEntity_Id(userId, postId)){
            throw new RuntimeException("이미 좋아요를 선택한 사용자");
        }
        MemberEntity memberEntity = memRepository.getReferenceById(userId);
        PostEntity postEntity = postRepository.getReferenceById(postId);
        PostLikeEntity postLikeEntity = new PostLikeEntity(memberEntity, postEntity);
        postLikeRepository.save(postLikeEntity);
    }
    //좋아요 취소
    public void unlikePost(Long postId, int userId){
        if(!postLikeRepository.existsByMemberEntity_IdAndPostEntity_Id(userId, postId)){
            throw new RuntimeException("좋아요 취소할 수 없음...");
        }
        postLikeRepository.deleteByMemberEntity_IdAndPostEntity_Id(userId, postId);
    }
}
