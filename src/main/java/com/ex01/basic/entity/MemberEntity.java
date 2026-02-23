package com.ex01.basic.entity;

import com.ex01.basic.entity.post.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="member_basic")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    private String fileName;
//mappedBy = "내가 연결하고자 하는 자식에 있는 변수 이름"
    @OneToMany(mappedBy = "memberEntity", orphanRemoval = true,
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostEntity> posts = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        if(this.role == null)
            this.role ="USER";
    }
}
