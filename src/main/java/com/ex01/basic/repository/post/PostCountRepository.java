package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Long> {
    // 해당 회원이 특정 게시글에 대한 데이터가 있는 확인
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
    //특정 게시글의 조회기록 개수를 세는 메서드(조회수는 얼마냐)
    long countByPostEntity_Id(Long postId);
}
