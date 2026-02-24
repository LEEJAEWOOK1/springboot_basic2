package com.ex01.basic.dto.post;

import com.ex01.basic.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostAllDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;

    private String username;
    private long count;
    private boolean liked;
    private long likedCount;

    public PostAllDto(PostEntity postEntity, long count, boolean liked, long likedCount){
        System.out.println(postEntity);
        BeanUtils.copyProperties(postEntity, this);
        this.username = postEntity.getMemberEntity().getUsername();
        this.count = count;
        this.liked = liked;
        this.likedCount = likedCount;
    }
}
