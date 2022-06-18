package com.company.dto.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleShortDTO {

    private String uuid;
    private String title;
    private LocalDateTime publicDate;
    private String description;

    public ArticleShortDTO(String uuid, String title, LocalDateTime publicDate, String description) {
        this.uuid = uuid;
        this.title = title;
        this.publicDate = publicDate;
        this.description = description;
    }
}
