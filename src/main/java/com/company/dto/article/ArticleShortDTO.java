package com.company.dto.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleShortDTO {

    @NotBlank
    private String uuid;
    @NotBlank
    private String title;
    private LocalDateTime publicDate;
    @NotBlank
    private String description;

    public ArticleShortDTO(String uuid, String title, LocalDateTime publicDate, String description) {
        this.uuid = uuid;
        this.title = title;
        this.publicDate = publicDate;
        this.description = description;
    }
}
