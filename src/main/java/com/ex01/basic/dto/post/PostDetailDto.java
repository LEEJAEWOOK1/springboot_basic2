package com.ex01.basic.dto.post;

import com.ex01.basic.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDetailDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;
    private int memberUserId;
    private String memberUsername;

    public PostDetailDto(PostEntity postEntity){
        BeanUtils.copyProperties(postEntity, this);
        this.memberUsername = postEntity.getMemberEntity().getUsername();
        this.memberUserId = postEntity.getMemberEntity().getId();
    }
}
