package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleShortInfoByCategory {

    private String Id;
    private String title;
    private String description;
    private LocalDateTime publishDate;
}
