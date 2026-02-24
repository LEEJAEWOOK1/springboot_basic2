package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
    long countByPostEntity_Id(Long postId);
    //특정 회원의 기록 삭제(해당 회원이 특정 게시글에 대한 만든 데이터 삭제)
    void deleteByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
}
