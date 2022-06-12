package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentCreateDTO {

    private String articleId;
    private Integer commentId;
    private String content;

}
