package com.company.dto.article;

import com.company.dto.profile.ProfileDTO;
import com.company.enums.ArticleLikeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleLikeDTO {

    private Integer id;
    private LocalDateTime createdDate;
    private ProfileDTO profileDTO;
    private ArticleDTO articleDTO;
    private ArticleLikeStatus status;
    private Boolean visible;
}
