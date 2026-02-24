package com.ex01.basic.entity.post;

import com.ex01.basic.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="post_count",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"member_id", "post_id"})})
@Getter @Setter
@NoArgsConstructor
//조회수 테이블 설정
public class PostCountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = true)
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private PostEntity postEntity;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp //생성될 때 초기화 된다.
    //@UpdateTimestamp
    private LocalDateTime createAt;

    public PostCountEntity(MemberEntity memberEntity, PostEntity postEntity){
        this.memberEntity = memberEntity;
        this.postEntity = postEntity;
    }
}
