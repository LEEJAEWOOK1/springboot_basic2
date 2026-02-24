package com.ex01.basic.entity.post;

import com.ex01.basic.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="basic_post")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
//포스트 테이블 설정
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="number", nullable = true)
    private MemberEntity memberEntity;

    private String title;
    private String content;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;
    //외래키 설정(PostCountEntity와 postEntity(PostCountEntity에 있는 변수) 연결)
    @OneToMany(mappedBy = "postEntity", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostCountEntity> postCounts = new ArrayList<>();
    //외래키 설정(PostLikeEntity와 postEntity(PostLikeEntity에 있는 변수) 연결)
    @OneToMany(mappedBy = "postEntity", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updateTime = LocalDateTime.now();
    }
}
